package com.hzxc.cms_manage_client.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: bwqcService01
 * @Package: com.bwqc.cms_manage_client.config
 * @ClassName: CmsClientConfig
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/11 20:46
 * @Version: 1.0
 */
@Configuration
public class CmsClientConfig {

    //队列bean的名称
    public static final String QUEUE_CMS_POSTPAGE = "queue_cms_postpage";
    //交换机的名称
    public static final String EX_ROUTING_CMS_POSTPAGE="ex_routing_cms_postpage";
    @Value("${bwqc.mq.queue}")
    private String queue;

    @Value("${bwqc.mq.routingKey}")
    private String routingKey;
    //建立交换机
    @Bean(EX_ROUTING_CMS_POSTPAGE)
    public Exchange exchange(){
        return ExchangeBuilder.directExchange(EX_ROUTING_CMS_POSTPAGE).durable(true).build();
    }
    //建立消息队列
    @Bean(QUEUE_CMS_POSTPAGE)
    public Queue queue(){
        return new Queue(queue);
    }
    //进行连接
    @Bean
    public Binding binding(@Qualifier(EX_ROUTING_CMS_POSTPAGE) Exchange exchange,
                           @Qualifier(QUEUE_CMS_POSTPAGE)Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(routingKey).noargs();
    }
}
