package com.server.health;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ServerhealthApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerhealthApplication.class, args);
	}

}
