package com.hzxc.cms_manage_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ProjectName: bwqcService01
 * @Package: com.bwqc.cms_manage_client
 * @ClassName: CmsManageClientApplication
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/11 20:43
 * @Version: 1.0
 */
@SpringBootApplication
@EntityScan("com.hzxc.framework.domain.cms")//扫描实体类
@ComponentScan(basePackages = "com.hzxc.framework")//扫描通用包
@ComponentScan(basePackages = "com.hzxc.cms_manage_client")//扫描通用包
public class CmsManageClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsManageClientApplication.class,args);
    }
}
