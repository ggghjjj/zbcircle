package com.zb.search.config;

import cn.itcast.hotel.constant.MqConstants;
import com.zb.search.constants.MqConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MqConfig {
    @Bean
    public TopicExchange blogTopicExchange() {
        return new TopicExchange(MqConstants.BLOG_EXCHANGE,true,false);
    }

    @Bean
    public Queue blogInsertQueue() {
        return new Queue(MqConstants.BLOG_INSERT_QUEUE,true);
    }

    @Bean
    public Queue blogDeleteQueue() {
        return new Queue(MqConstants.BLOG_DELETE_QUEUE,true);
    }

    @Bean
    public Binding blogInsertQueuebinding() {
        return BindingBuilder.bind(blogInsertQueue()).to(blogTopicExchange()).with(MqConstants.BLOG_INSERT_KEY);
    }
    @Bean
    public Binding blogDeleteQueuebinding() {
        return BindingBuilder.bind(blogDeleteQueue()).to(blogTopicExchange()).with(MqConstants.BLOG_DELETE_QUEUE);
    }
}
