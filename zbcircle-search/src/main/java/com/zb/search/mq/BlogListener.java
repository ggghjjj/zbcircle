package com.zb.search.mq;

import com.zb.search.constants.MqConstants;
import com.zb.search.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BlogListener {

    @Autowired
    private BlogService blogService;

    @RabbitListener(queues = MqConstants.BLOG_INSERT_QUEUE)
    public void listenHotelInsertOrUpdate(Long id) {
        log.info("请求insert发过来了"+id);
        blogService.insertById(id);
    }


    @RabbitListener(queues = MqConstants.BLOG_DELETE_QUEUE)
    public void listenHotelDeleteOrUpdate(Long id) {
        log.info("请求delete发过来了"+id);
        blogService.deleteById(id);
    }

}
