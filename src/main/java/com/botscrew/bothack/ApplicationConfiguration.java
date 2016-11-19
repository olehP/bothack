package com.botscrew.bothack;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableAsync;

import ai.api.AIConfiguration;
import ai.api.AIDataService;

@Configuration
@EnableAsync
@PropertySource("classpath:custom.properties")
public class ApplicationConfiguration {
	@Value("${api.ai.key}")
	private String apiAiKey;

	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("style/messages");
		return messageSource;
	}

	@Bean
	public AIDataService aiDataService() {
		return new AIDataService(new AIConfiguration(apiAiKey));
	}
}
