package com.xaut;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class BallApplication {

	public static void main(String[] args) {
		SpringApplication.run(BallApplication.class, args);
	}
}
