package com.zb.chat.service;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zb.chat.feignclient.UserServiceClient;
import com.zb.chat.pojo.Record;
import com.zb.chat.pojo.User;
import com.zb.chat.utils.SensitiveWordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@ServerEndpoint(value = "/chat/websocket/{username}")
@Component
public class WebSocketServer {

    private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    final public static ConcurrentHashMap<String, WebSocketServer> users = new ConcurrentHashMap<>();

    private Session session = null;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private RecordService recordService;
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException {
        this.session = session;
        User user = userServiceClient.getUserByName(username);
        if(user==null) {
            this.session.close();
        }else {
            users.put(username,this);
        }
        log.info("有新用户加入，username={}, 当前在线人数为：{}", username, users.size());
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        result.set("users", array);
        for (Object key : users.keySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("username", key);
            array.add(jsonObject);
        }
        sendAllMessage(JSONUtil.toJsonStr(result));  // 后台发送消息给所有的客户端
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session, @PathParam("username") String username) {
        users.remove(username);
        log.info("有一连接关闭，移除username={}的用户session, 当前在线人数为：{}", username, users.size());
    }

    /**
     * 收到客户端消息后调用的方法
     * 后台收到客户端发送过来的消息
     * onMessage 是一个消息的中转站
     * 接受 浏览器端 socket.send 发送过来的 json数据
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("username") String username) {
        log.info("服务端收到用户username={}的消息:{}", username, message);
        JSONObject obj = JSONUtil.parseObj(message);
        String toUsername = obj.getStr("to"); // to表示发送给哪个用户，比如 admin
        String text = obj.getStr("text"); // 发送的消息文本  hello
        // {"to": "admin", "text": "聊天文本"}

        SensitiveWordUtil.replaceSensitiveWord(message, 1, "*");

        Record record = new Record().builder().fromName(username).toName(toUsername).time(dateFormat.format(LocalDateTime.now())).content(message).build();
        recordService.insert(record);


        Session toSession = users.get(toUsername).session; // 根据 to用户名来获取 session，再通过session发送消息文本
        if (toSession != null) {
            // 服务器端 再把消息组装一下，组装后的消息包含发送人和发送的文本内容
            // {"from": "zhang", "text": "hello"}
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("from", username);  // from 是 zhang
            jsonObject.set("text", text);  // text 同上面的text
            this.sendMessage(jsonObject.toString(), toSession);
            log.info("发送给用户username={}，消息：{}", toUsername, jsonObject.toString());
        } else {
            log.info("发送失败，未找到用户username={}的session", toUsername);
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 服务端发送消息给客户端
     */
    private void sendMessage(String message, Session toSession) {
        try {
            log.info("服务端给客户端[{}]发送消息{}", toSession.getId(), message);
            toSession.getBasicRemote().sendText(message);
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }

    /**
     * 服务端发送消息给所有客户端
     */
    private void sendAllMessage(String message) {
        try {
            for (WebSocketServer webSocketServer : users.values()) {
                Session session1 = webSocketServer.session;
                log.info("服务端给客户端[{}]发送消息{}", session1.getId(), message);
                session1.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }
}