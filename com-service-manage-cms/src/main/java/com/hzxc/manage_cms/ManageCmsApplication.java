package com.hzxc.manage_cms;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
//@EnableDiscoveryClient //一个eureka从eureka server中发现服务
@SpringBootApplication
@EntityScan("com.hzxc.framework.domain.cms")//扫描实体类
@ComponentScan(basePackages = "com.hzxc.api")//扫描接口
@ComponentScan(basePackages = "com.hzxc.manage_cms")//扫描本项目
@ComponentScan(basePackages = "com.hzxc.framework")//扫描通用包
public class ManageCmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageCmsApplication.class,args);
    }
    /**
     *
     *
     * */
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }
}
