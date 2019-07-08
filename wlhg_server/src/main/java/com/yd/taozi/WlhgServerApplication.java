package com.yd.taozi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class WlhgServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WlhgServerApplication.class, args);
	}

}
