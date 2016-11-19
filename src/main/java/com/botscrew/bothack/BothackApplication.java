package com.botscrew.bothack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.botscrew.bothack.service.fake.ConsoleController;

@SpringBootApplication
public class BothackApplication {

	public static void main(String[] args) {
		SpringApplication.run(BothackApplication.class, args).getBean(ConsoleController.class).run();
	}
}
