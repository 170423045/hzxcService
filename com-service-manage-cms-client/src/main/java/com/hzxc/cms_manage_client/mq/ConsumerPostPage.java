package com.hzxc.cms_manage_client.mq;

import com.alibaba.fastjson.JSON;
import com.hzxc.cms_manage_client.service.CmsClientService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @ProjectName: bwqcService01
 * @Package: com.bwqc.cms_manage_client.mq
 * @ClassName: ConsumerPostPage
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/12 10:52
 * @Version: 1.0
 */
@Component
public class ConsumerPostPage  {
    @Autowired
    private CmsClientService cmsClientService;

    @RabbitListener(queues = "${bwqc.mq.queue}")
    public void postPage(String msg){
        Map map = JSON.parseObject(msg, Map.class);
        String pageId = (String) map.get("pageId");
        cmsClientService.savePageToServerPath(pageId);
    }
}
