package com.botscrew.bothack.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.botscrew.bothack.entity.User;
import com.botscrew.bothack.messages.MessageKey;
import com.botscrew.bothack.messages.MessagesHolder;
import com.botscrew.bothack.model.UserId;
import com.botscrew.bothack.service.GeneralResponseService;
import com.botscrew.bothack.service.SendMessageService;

@Service
public class GeneralResponseServiceImpl implements GeneralResponseService {
	@Autowired
	private MessagesHolder messagesHolder;
	@Autowired
	private SendMessageService sendMessageService;

	@Override
	public void sendGetStartedMessage(User user) {
		sendMessageService.sendSimpleMessage(new UserId(user.getChatId()),
				messagesHolder.getRandomMessage(MessageKey.WELCOME, user.getStyle()));
	}
}
