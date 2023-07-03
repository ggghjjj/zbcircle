package com.zb.search.constants;

public class MqConstants {
    /**
     * 交换机
     */
    public final static String BLOG_EXCHANGE = "blog.topic";
    /**
     * 监听新增和修改队列
     */
    public final static String BLOG_INSERT_QUEUE = "blog.insert.queue";

    /**
     * 监听删除的队列
     */
    public final static String BLOG_DELETE_QUEUE = "blog.delete.queue";

    /**
     * 新增或修改的RoutingKey
     */
    public final static String BLOG_INSERT_KEY = "blog.insert";

    /**
     * 删除的RoutingKey
     */
    public final static String BLOG_DELETE_KEY = "blog.delete";
}
