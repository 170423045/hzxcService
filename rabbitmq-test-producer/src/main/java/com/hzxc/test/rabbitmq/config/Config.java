package com.hzxc.test.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: bwqcService01
 * @Package: com.bwqc.rabbitmqtestproducer.config
 * @ClassName: Config
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/11 16:51
 * @Version: 1.0
 */

@Configuration
public class Config {
    public static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    public static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    public static final String EXCHANGE_TOPICS_INFORM="exchange_topics_inform";
    public static final String ROUTINGKEY_EMAIL="inform.#.email.#";
    public static final String ROUTINGKEY_SMS="inform.#.sms.#";
    //声明交换机
    @Bean(EXCHANGE_TOPICS_INFORM)
    public Exchange exchange(){
        return ExchangeBuilder.topicExchange(EXCHANGE_TOPICS_INFORM).durable(true).build();
    }
    //声明队列
    @Bean(QUEUE_INFORM_EMAIL)//email队列
    public Queue queueEmail(){
        return new Queue(QUEUE_INFORM_EMAIL);
    }
    @Bean(QUEUE_INFORM_SMS)//sms队列
    public  Queue queueSms(){
        return new Queue(QUEUE_INFORM_SMS);
    }
    //绑定交换机
    @Bean
    public Binding bindingEmail(@Qualifier(QUEUE_INFORM_EMAIL) Queue queue,
                                @Qualifier(EXCHANGE_TOPICS_INFORM)Exchange exchange){

        return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY_EMAIL).noargs();
    }
    @Bean
    public Binding bindingSms(@Qualifier(QUEUE_INFORM_SMS) Queue queue,
                                @Qualifier(EXCHANGE_TOPICS_INFORM)Exchange exchange){

        return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY_SMS).noargs();
    }
}
