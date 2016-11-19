package com.botscrew.bothack.processor.impl;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpClientErrorException;

import com.botscrew.bothack.entity.User;
import com.botscrew.bothack.exception.SystemException;
import com.botscrew.bothack.messages.Postback;
import com.botscrew.bothack.model.UserId;
import com.botscrew.bothack.model.incomming.Attachment;
import com.botscrew.bothack.model.incomming.Coordinates;
import com.botscrew.bothack.model.incomming.MessageRecieved;
import com.botscrew.bothack.model.incomming.Messaging;
import com.botscrew.bothack.model.incomming.Payload;
import com.botscrew.bothack.processor.MessagesProcessor;
import com.botscrew.bothack.service.GeneralResponseService;
import com.botscrew.bothack.service.SendMessageService;
import com.botscrew.bothack.service.UserService;

@Service
public class MessagesProcessorImpl implements MessagesProcessor {
	private static final Logger LOGGER = LogManager.getLogger(MessagesProcessorImpl.class);

	@Autowired
	private SendMessageService sendMessageService;
	@Autowired
	private GeneralResponseService generalResponseService;
	@Autowired
	private UserService userService;

	@Override
	public void processMessage(MessageRecieved message) {
		try {
			message.getEntry().forEach(entry -> {
				entry.getMessaging().forEach(messaging -> {
					User user = userService.createIfNotExist(messaging.getSender());
					Optional<String> textMessageOpt = getTextMessage(messaging);
					Optional<String> payloadOpt = getPayload(messaging);
					Optional<Coordinates> coordinatesOpt = getCoordinates(messaging);
					if (coordinatesOpt.isPresent()) {
						// do something with coordinates
						return;
					}
					if (textMessageOpt.isPresent()) {
						// do something with text
						sendMessageService.sendSimpleMessage(new UserId(), "text: " + textMessageOpt.get());
						return;
					}
					if (payloadOpt.isPresent()) {
						// do something with payload
						processPayload(user, payloadOpt.get());
						sendMessageService.sendSimpleMessage(new UserId(), "postback: " + payloadOpt.get());
					}
				});
			});
		} catch (HttpClientErrorException e) {
			LOGGER.error(e.getResponseBodyAsString(), e);
		} catch (SystemException e) {
			LOGGER.error("System exception", e);
		}
	}

	private void processPayload(User user, String payload) {
		if (Postback.GET_STARTED.payload().equals(payload)) {
			generalResponseService.sendGetStartedMessage(user);
		}
	}

	private Optional<String> getPayload(Messaging messaging) {
		if (messaging.getPostback() != null) {
			return Optional.ofNullable(messaging.getPostback().getPayload());
		}
		return Optional.empty();
	}

	private Optional<String> getTextMessage(Messaging messaging) {
		if (messaging.getMessage() != null) {
			return Optional.ofNullable(messaging.getMessage().getText());
		}
		return Optional.empty();
	}

	private Optional<Coordinates> getCoordinates(Messaging messaging) {
		if (messaging.getMessage() != null) {
			List<Attachment> attachments = messaging.getMessage().getAttachments();
			if (!CollectionUtils.isEmpty(attachments)) {
				Payload payload = attachments.get(0).getPayload();
				if (payload != null) {
					return Optional.ofNullable(payload.getCoordinates());
				}
			}
		}
		return Optional.empty();
	}
}
