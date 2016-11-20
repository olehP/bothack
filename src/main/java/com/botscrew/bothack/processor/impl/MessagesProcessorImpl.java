package com.botscrew.bothack.processor.impl;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpClientErrorException;

import com.botscrew.bothack.dao.UserDAO;
import com.botscrew.bothack.dao.UserRequestDAO;
import com.botscrew.bothack.entity.ChatState;
import com.botscrew.bothack.entity.User;
import com.botscrew.bothack.entity.UserRequest;
import com.botscrew.bothack.exception.SystemException;
import com.botscrew.bothack.messages.Postback;
import com.botscrew.bothack.model.incomming.Attachment;
import com.botscrew.bothack.model.incomming.Coordinates;
import com.botscrew.bothack.model.incomming.MessageRecieved;
import com.botscrew.bothack.model.incomming.Messaging;
import com.botscrew.bothack.model.incomming.Payload;
import com.botscrew.bothack.processor.MessagesProcessor;
import com.botscrew.bothack.service.GeneralResponseService;
import com.botscrew.bothack.service.UserService;

@Service
public class MessagesProcessorImpl implements MessagesProcessor {
	private static final Logger LOGGER = LogManager.getLogger(MessagesProcessorImpl.class);

	@Autowired
	private GeneralResponseService generalResponseService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserRequestDAO userRequestDAO;
	@Autowired
	private UserDAO userDAO;

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
						processCoordinates(user, coordinatesOpt.get());
						return;
					}
					if (textMessageOpt.isPresent()) {
						sendNextQuestion(user);
						return;
					}
					if (payloadOpt.isPresent()) {
						processPayload(user, payloadOpt.get());
					}
				});
			});
		} catch (HttpClientErrorException e) {
			LOGGER.error(e.getResponseBodyAsString(), e);
		} catch (SystemException e) {
			LOGGER.error("System exception", e);
		}
	}

	private void processCoordinates(User user, Coordinates coordinates) {
		UserRequest userRequest = userRequestDAO.findByUserAndIsActiveTrue(user);
		userRequest.setLatitude(coordinates.getLatitude());
		userRequest.setLongitude(coordinates.getLongitude());
		userRequestDAO.save(userRequest);
		generalResponseService.sendRecommendations(userRequest);
		user.setChatState(ChatState.PRODUCT_LIST);
		userDAO.save(user);
	}

	private void sendNextQuestion(User user) {
		switch (user.getChatState()) {
		case MUSIC:
			generalResponseService.askAboutMusic(user);
			break;
		case LOYALTY:
			generalResponseService.askAboutLoyalty(user);
			break;
		case REQUEST:
			generalResponseService.askWhatToDo(user);
			break;
		case SEARCH:
			generalResponseService.sendSearchResults(user);
			break;
		case PRODUCT_LIST:
			generalResponseService.sendListResponse(user);
			break;
		case ANYTHINGELSE:
			generalResponseService.processAnythingElse(user);
			break;
		case USE_MILES:
			generalResponseService.sendUseMiles(user);
			break;
		case YES_NO:
			generalResponseService.sendYesNo(user);
			break;
		case FINAL_PRICE:
			generalResponseService.sendFinalPrice(user);
			break;
		default:
			break;
		}

	}

	private void processPayload(User user, String payload) {
		if (Postback.GET_STARTED.payload().equals(payload)) {
			generalResponseService.sendGetStartedMessage(user);
		} else if (Postback.MUSIC_CONNECT.payload().equals(payload)) {
			user.setChatState(ChatState.LOYALTY);
			userDAO.save(user);
			sendNextQuestion(user);
		} else if (Postback.LOYALTY_CONNECT.payload().equals(payload)) {
			user.setChatState(ChatState.REQUEST);
			userDAO.save(user);
			sendNextQuestion(user);
		} else if (payload.startsWith(Postback.BUY.payload())) {
			double price = Double.parseDouble(payload.substring(payload.indexOf("_") + 1));
			UserRequest userRequest = userRequestDAO.findByUserAndIsActiveTrue(user);
			userRequest.setPrice(price);
			userRequestDAO.save(userRequest);
			user.setChatState(ChatState.USE_MILES);
			userDAO.save(user);
			sendNextQuestion(user);
		} else {
			sendNextQuestion(user);
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
