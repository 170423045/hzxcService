package com.hzxc.govern.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @ProjectName: bwqcService01
 * @Package: com.bwqc.govern.center
 * @ClassName: GovernCenterApplication
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/13 10:21
 * @Version: 1.0
 */
@EnableEurekaServer
@SpringBootApplication
public class GovernCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(GovernCenterApplication.class,args);
    }
}
