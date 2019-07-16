package com.hzxc.test.freemarker;

/**
 * @ProjectName: bwqcService01
 * @Package: com.bwqc.test.freemarker
 * @ClassName: FreemarkerTestApplication
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/9 21:49
 * @Version: 1.0
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class FreemarkerTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(FreemarkerTestApplication.class,args);
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }
}