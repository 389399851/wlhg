package com.yd.taozi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableHystrix
@EnableDiscoveryClient
@SpringBootApplication//(exclude = DataSourceAutoConfiguration.class)
public class WlhgAdminApplication {
	public static void main(String[] args) {
		SpringApplication.run(WlhgAdminApplication.class, args);
	}

}
