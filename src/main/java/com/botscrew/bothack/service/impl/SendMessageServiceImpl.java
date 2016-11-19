package com.botscrew.bothack.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.botscrew.bothack.model.Button;
import com.botscrew.bothack.model.UserId;
import com.botscrew.bothack.model.outgoing.DomainWhiteList;
import com.botscrew.bothack.model.outgoing.buttons.ButtonTemplateRequest;
import com.botscrew.bothack.model.outgoing.generic.ListTemplateMessageElement;
import com.botscrew.bothack.model.outgoing.generic.MessageElement;
import com.botscrew.bothack.model.outgoing.generic.MessageRequest;
import com.botscrew.bothack.model.outgoing.quickreply.QuickRepliesRequest;
import com.botscrew.bothack.model.outgoing.quickreply.QuickReply;
import com.botscrew.bothack.model.outgoing.simple.SimpleMessage;
import com.botscrew.bothack.model.outgoing.simple.SimpleMessageRequest;
import com.botscrew.bothack.service.SendMessageService;

@Primary
@Service
public class SendMessageServiceImpl implements SendMessageService {
	@Value("${facebook.messaging.url}")
	private String MESSAGING_URL;
	@Value("${facebook.token}")
	private String token;
	@Value("${facebook.setwhitelisting.url}")
	private String WHITELISTING_URL;
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public void sendSimpleMessage(UserId recipient, String text) {
		SimpleMessageRequest request = new SimpleMessageRequest();
		request.setRecipient(recipient);
		SimpleMessage message = new SimpleMessage();
		message.setText(text);
		request.setMessage(message);
		restTemplate.postForObject(MESSAGING_URL + token, request, String.class);
	}

	@Override
	public void sendDomainWhiteList(DomainWhiteList domainWhiteList) {
		restTemplate.postForObject(WHITELISTING_URL + token, domainWhiteList, String.class);
	}

	@Override
	public void sendGenericMessages(UserId recipient, List<MessageElement> elements) {
		MessageRequest messageRequest = MessageRequest.getBuilder().elements(elements).recipient(recipient).build();
			restTemplate.postForObject(MESSAGING_URL + token, messageRequest, String.class);
	}

	@Override
	public void sendListTemplate(UserId recipient, List<ListTemplateMessageElement> elements) {
		List<MessageElement> messageElements = new ArrayList<>(elements);
		MessageRequest messageRequest = MessageRequest.getBuilder().elements(messageElements).recipient(recipient).buildListTemplate();
		restTemplate.postForObject(MESSAGING_URL + token, messageRequest, MessageRequest.class);
	}

	@Override
	public void sendButtonsMessage(UserId recipient, List<Button> buttons, String text) {
		ButtonTemplateRequest request = ButtonTemplateRequest.getBuilder().buttons(buttons).text(text)
				.recipient(recipient).build();
		restTemplate.postForObject(MESSAGING_URL + token, request, String.class);
	}

	@Override
	public void sendQuickRepliesMessage(UserId recipient, List<QuickReply> quickReplies, String text) {
		QuickRepliesRequest request = QuickRepliesRequest.getBuilder().quickReplies(quickReplies).text(text)
				.recipient(recipient).build();
		restTemplate.postForObject(MESSAGING_URL + token, request, String.class);
	}

}
