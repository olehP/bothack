package com.botscrew.bothack.messages;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import com.botscrew.bothack.exception.SystemException;
import com.botscrew.bothack.model.Pair;

@Component("styledMessageHolder")
public class MessagesHolder {
	private Map<Pair<MessageKey, Style>, Integer> messagesCounts = new HashMap<>();
	private Random random = new Random();
	@Autowired
	private MessageSource messageSource;

	@PostConstruct
	public void init() {
		for (Style style : Style.values()) {
			for (MessageKey messageKey : MessageKey.values()) {
				int count = 0;
				while (hasMessage(messageKey.value() + "." + count, style)) {
					count++;
				}
				if (count > 0) {
					messagesCounts.put(new Pair<>(messageKey, style), count);
				}
			}
		}
	}

	private boolean hasMessage(String key, Style style) {
		try {
			getMessage(key, style);
			return true;
		} catch (NoSuchMessageException e) {
			return false;
		}
	}

	private String getMessage(String key, Style style) {
		return messageSource.getMessage(key, null,
				new java.util.Locale(style.name().toLowerCase(), style.name().toLowerCase()));
	}

	public String getMessage(MessageKey key, Style style) {
		return getMessage(key.value(), style);
	}

	public String getMessage(Postback key, Style style) {
		return getMessage(key.titleKey(), style);
	}

	public String getTemplateMessage(MessageKey key, Style style, Map<String, String> parameters) {
		String templateMessage = messageSource.getMessage(key.value(), null,
				new java.util.Locale(style.name().toLowerCase(), style.name().toLowerCase()));
		return fillTemplate(parameters, templateMessage);
	}

	public String getRandomMessage(MessageKey messageKey, Style style) {
		if (!messagesCounts.containsKey(new Pair<>(messageKey, style))) {
			throw new SystemException("Message key " + messageKey.value() + " has no options");
		}
		return getMessage(messageKey.value() + "." + random.nextInt(messagesCounts.get(new Pair<>(messageKey, style))),
				style);
	}

	public String getTemplateRandomMessage(MessageKey messageKey, Map<String, String> parameters, Style style) {
		return fillTemplate(parameters, getRandomMessage(messageKey, style));

	}

	private String fillTemplate(Map<String, String> parameters, String templateMessage) {
		for (Entry<String, String> entry : parameters.entrySet()) {
			templateMessage = templateMessage.replace(entry.getKey(), entry.getValue());
		}
		return templateMessage;
	}
}
