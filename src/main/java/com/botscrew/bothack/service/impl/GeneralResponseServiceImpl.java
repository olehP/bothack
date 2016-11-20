package com.botscrew.bothack.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.botscrew.bothack.dao.UserDAO;
import com.botscrew.bothack.dao.UserRequestDAO;
import com.botscrew.bothack.entity.ChatState;
import com.botscrew.bothack.entity.User;
import com.botscrew.bothack.entity.UserRequest;
import com.botscrew.bothack.messages.MessageKey;
import com.botscrew.bothack.messages.MessagesHolder;
import com.botscrew.bothack.messages.Postback;
import com.botscrew.bothack.messages.Style;
import com.botscrew.bothack.model.Button;
import com.botscrew.bothack.model.UserId;
import com.botscrew.bothack.model.ebay.EbayProduct;
import com.botscrew.bothack.model.outgoing.generic.MessageElement;
import com.botscrew.bothack.model.outgoing.quickreply.QuickReply;
import com.botscrew.bothack.service.EbayService;
import com.botscrew.bothack.service.GeneralResponseService;
import com.botscrew.bothack.service.SendMessageService;
import com.botscrew.bothack.util.GoogleMapsUtil;

@Service
public class GeneralResponseServiceImpl implements GeneralResponseService {
	@Autowired
	private MessagesHolder messagesHolder;
	@Autowired
	private SendMessageService sendMessageService;
	@Autowired
	private EbayService ebayService;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private UserRequestDAO userRequestDAO;

	@Override
	public void sendGetStartedMessage(User user) {
		UserRequest userRequest = userRequestDAO.findByUserAndIsActiveTrue(user);
		if (userRequest != null) {
			userRequest.setIsActive(false);
			userRequestDAO.save(userRequest);
		}
		userRequest = new UserRequest();
		userRequest.setIsActive(true);
		userRequest.setUser(user);
		userRequestDAO.save(userRequest);
		user.setChatState(ChatState.MUSIC);
		userDAO.save(user);
		sendMessageService.sendSimpleMessage(new UserId(user.getChatId()),
				messagesHolder.getMessage(MessageKey.WELCOME, user.getStyle()));
		askAboutMusic(user);

	}

	@Override
	public void askAboutMusic(User user) {
		sendMessageService.sendSimpleMessage(new UserId(user.getChatId()),
				messagesHolder.getMessage(MessageKey.MUSIC, user.getStyle()));
		sendMessageService.sendGenericMessages(new UserId(user.getChatId()), ServiceProvider.getMusicServices());

	}

	@Override
	public void askAboutLoyalty(User user) {
		sendMessageService.sendSimpleMessage(new UserId(user.getChatId()),
				messagesHolder.getMessage(MessageKey.LOYALTY, user.getStyle()));
		sendMessageService.sendGenericMessages(new UserId(user.getChatId()), ServiceProvider.getLoyaltyServices());
		user.setChatState(ChatState.REQUEST);
		userDAO.save(user);

	}

	@Override
	public void askWhatToDo(User user) {
		sendMessageService.sendQuickRepliesMessage(new UserId(user.getChatId()),
				Arrays.asList(QuickReply.createLocationQuickReply()),
				messagesHolder.getMessage(MessageKey.WHAT_TO_DO, user.getStyle()));
		if (user.getStyle() == Style.RAP) {
			user.setChatState(ChatState.PRODUCT_LIST);
		} else {
			user.setChatState(ChatState.SEARCH);
		}
		userDAO.save(user);
	}

	@Override
	public void sendRecommendations(UserRequest userRequest) {
		sendMessageService.sendSimpleMessage(new UserId(userRequest.getUser().getChatId()),
				messagesHolder.getMessage(MessageKey.PRERESULT, userRequest.getUser().getStyle()));
		List<EbayProduct> snapbacks = ebayService.getProducts("snapback", userRequest.getLatitude(),
				userRequest.getLongitude(), 0, 1000, 1, 3);
		List<EbayProduct> hiphop = ebayService.getProducts("hiphop", userRequest.getLatitude(),
				userRequest.getLongitude(), 0, 1000, 1, 3);
		List<MessageElement> elements = new ArrayList<>();
		snapbacks.forEach(item -> {
			if (item.getPhotoUrl() != null) {
				MessageElement element = new MessageElement();
				element.setTitle(item.getTitle());
				element.setSubtitle(item.getPrice().toString() + "€");
				element.setImageUrl(item.getPhotoUrl());
				List<Button> buttons = new ArrayList<>();
				buttons.add(Button.createWebUrlButton("View on map",
						GoogleMapsUtil.generateDestinationUrl(item.getLatitude(), item.getLongitude())));
				if (item.getPhone() != null) {
					buttons.add(Button.createCallButton("Call seller", item.getPhone()));
				}
				element.setButtons(buttons);
				elements.add(element);
			}
		});
		MessageElement mainElement = new MessageElement();
		mainElement.setTitle("Gangster Chain Dollar Sign Necklace");
		mainElement.setImageUrl("https://s12.postimg.org/ak8cynnrx/gold.jpg");
		mainElement.setSubtitle("125€");

		mainElement.setButtons(Arrays.asList(Button.createPostbackButton("View on map", "sss"),
				Button.createPostbackButton("Call seller", "sss")));
		elements.add(mainElement);
		hiphop.forEach(item -> {
			if (item.getPhotoUrl() != null) {
				MessageElement element = new MessageElement();
				element.setTitle(item.getTitle());
				element.setSubtitle(item.getPrice().toString() + "€");
				element.setImageUrl(item.getPhotoUrl());
				List<Button> buttons = new ArrayList<>();
				buttons.add(Button.createWebUrlButton("View on map",
						GoogleMapsUtil.generateDestinationUrl(item.getLatitude(), item.getLongitude())));
				if (item.getPhone() != null) {
					buttons.add(Button.createCallButton("Call seller", item.getPhone()));
				}
				element.setButtons(buttons);
				elements.add(element);
			}
		});
		User user = userRequest.getUser();
		sendMessageService.sendGenericMessages(new UserId(user.getChatId()), elements);
	}

	@Override
	public void sendListResponse(User user) {
		if (user.getStyle() == Style.RAP) {
			sendMessageService.sendSimpleMessage(new UserId(user.getChatId()),
					messagesHolder.getMessage(MessageKey.ANYTHING_ELSE, user.getStyle()));
			user.setChatState(ChatState.ANYTHINGELSE);
			userDAO.save(user);
		}

	}

	@Override
	public void processAnythingElse(User user) {
		if (user.getStyle() == Style.RAP) {
			sendMessageService.sendSimpleMessage(new UserId(user.getChatId()),
					messagesHolder.getMessage(MessageKey.PING_REQUEST, user.getStyle()));
			user.setChatState(ChatState.PING);
			userDAO.save(user);
		} else {
			sendMessageService.sendSimpleMessage(new UserId(user.getChatId()),
					messagesHolder.getMessage(MessageKey.LAST, user.getStyle()));
		}

	}

	@Override
	public void sendSearchResults(User user) {
		List<EbayProduct> products = ebayService.getProducts("iphone 7", null, null, 600, 10000, 1, 5);
		List<MessageElement> elements = new ArrayList<>();
		products.forEach(item -> {
			if (item.getPhotoUrl() != null) {
				MessageElement element = new MessageElement();
				element.setTitle(item.getTitle());
				element.setSubtitle(item.getPrice().toString() + "€");
				element.setImageUrl(item.getPhotoUrl());
				List<Button> buttons = new ArrayList<>();
				buttons.add(Button.createWebUrlButton("More info",
						GoogleMapsUtil.generateDestinationUrl(item.getLatitude(), item.getLongitude())));

				buttons.add(Button.createPostbackButton("Buy", Postback.BUY.payload() + item.getPrice().toString()));

				element.setButtons(buttons);
				elements.add(element);
			}
		});
		sendMessageService.sendGenericMessages(new UserId(user.getChatId()), elements);
	}

	@Override
	public void sendUseMiles(User user) {
		sendMessageService.sendQuickRepliesMessage(new UserId(user.getChatId()),
				Arrays.asList(QuickReply.createTextQuickReply("Yes", Postback.YES.payload()),
						QuickReply.createTextQuickReply("No", Postback.NO.payload())),
				messagesHolder.getMessage(MessageKey.MILES, user.getStyle()));
		user.setChatState(ChatState.FINAL_PRICE);
		userDAO.save(user);

	}

	@Override
	public void sendFinalPrice(User user) {
		UserRequest userRequest = userRequestDAO.findByUserAndIsActiveTrue(user);
		Map<String, String> params = new HashMap<>();
		params.put("$price$", userRequest.getPrice().toString() + "€");
		sendMessageService.sendQuickRepliesMessage(new UserId(user.getChatId()),
				Arrays.asList(QuickReply.createTextQuickReply("Buy", "BB")),
				messagesHolder.getTemplateMessage(MessageKey.FINAL_PRICE, user.getStyle(), params));
		user.setChatState(ChatState.YES_NO);
		userDAO.save(user);

	}

	@Override
	public void sendYesNo(User user) {
		UserRequest userRequest = userRequestDAO.findByUserAndIsActiveTrue(user);
		Map<String, String> params = new HashMap<>();
		params.put("$earning$", userRequest.getPrice() * 50 + "");
		sendMessageService.sendSimpleMessage(new UserId(user.getChatId()),
				messagesHolder.getTemplateMessage(MessageKey.ANYTHING_ELSE, user.getStyle(), params));
		user.setChatState(ChatState.ANYTHINGELSE);
		userDAO.save(user);

	}
}
