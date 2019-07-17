package com.hzxc.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/*
* @author: chenSong
*@date: 2019/7/17 date :19:50:34
*
* */

@EnableEurekaServer   //开启注册中心
@SpringBootApplication
public class HzxcEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HzxcEurekaServerApplication.class, args);
	}

}
