package com.botscrew.bothack.init;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.botscrew.bothack.messages.Postback;
import com.botscrew.bothack.model.outgoing.welcome.WelcomeMessageRequest;

@Service
public class InitializationService {
	@Value("${refresh.welcome.message:true}")
	private Boolean refreshWelcomeMessage;
	@Value("${facebook.welcome.message.url}")
	private String welcomeMessageUrl;
	@Value("${facebook.token}")
	private String token;

	@Autowired
	private RestTemplate restTemplate;

	@Transactional
	private void tryToRefreshWelcomeMessage() {
		if (refreshWelcomeMessage) {
			restTemplate.postForObject(welcomeMessageUrl + token,
					WelcomeMessageRequest.getFullRequest(Postback.GET_STARTED.payload()), String.class);
		}
	}

	@PostConstruct
	public void init() {
		tryToRefreshWelcomeMessage();
	}
}
