package com.hzxc.manage_cms.config;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: bwqcService01
 * @Package: com.bwqc.manage_cms.config
 * @ClassName: RabbitMQConfig
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/12 11:25
 * @Version: 1.0
 */
@Configuration
public class RabbitMQConfig {
    //队列bean的名称
    public static final String QUEUE_CMS_POSTPAGE = "queue_cms_postpage";
    //交换机的名称
    public static final String EX_ROUTING_CMS_POSTPAGE="ex_routing_cms_postpage";

    //建立交换机
    @Bean(EX_ROUTING_CMS_POSTPAGE)
    public Exchange exchange(){
        return ExchangeBuilder.directExchange(EX_ROUTING_CMS_POSTPAGE).durable(true).build();
    }

}
