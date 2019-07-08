package com.yd.taozi;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
@EnableEurekaClient
@SpringBootApplication
//@MapperScan("com.yd.taozi.mapper")
public class WlhgProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(WlhgProviderApplication.class, args);
	}

}
