package com.hzxc.test.rabbitmq.mq;

import com.hzxc.test.rabbitmq.config.Config;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: bwqcService01
 * @Package: com.bwqc.test.rabbitmq.mq
 * @ClassName: Customer1
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/11 17:56
 * @Version: 1.0
 */
@Component
public class Customer1 {

    @RabbitListener(queues = {Config.QUEUE_INFORM_EMAIL})
    public void sendEmail(String msg, Message message, Channel channel){
        System.out.println(msg);
    }
}
