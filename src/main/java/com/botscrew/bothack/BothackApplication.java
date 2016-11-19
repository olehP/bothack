package com.botscrew.bothack;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.botscrew.bothack.service.fake.ConsoleController;
import com.wrapper.spotify.exceptions.WebApiException;

@SpringBootApplication
public class BothackApplication {

	
	public static void main(String[] args) throws IOException, WebApiException {
		
		SpringApplication.run(BothackApplication.class, args).getBean(ConsoleController.class).run();
		
	}
}
