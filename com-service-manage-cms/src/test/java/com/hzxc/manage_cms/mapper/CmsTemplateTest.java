package com.hzxc.manage_cms.mapper;

import com.hzxc.manage_cms.service.CmsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ProjectName: bwqcService01
 * @Package: com.bwqc.manage_cms.mapper
 * @ClassName: CmsTemplateTest
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/10 22:33
 * @Version: 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsTemplateTest {
    @Autowired
    CmsService cmsService;
    @Test
    public void staticPage(){
        String content = cmsService.getHtml("5d245ee488b63a3954029a58");
        System.out.println(content);
    }
}
