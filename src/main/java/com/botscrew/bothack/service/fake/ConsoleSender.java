package com.botscrew.bothack.service.fake;

import java.util.List;

import org.springframework.stereotype.Service;

import com.botscrew.bothack.model.Button;
import com.botscrew.bothack.model.UserId;
import com.botscrew.bothack.model.outgoing.DomainWhiteList;
import com.botscrew.bothack.model.outgoing.generic.ListTemplateMessageElement;
import com.botscrew.bothack.model.outgoing.generic.MessageElement;
import com.botscrew.bothack.model.outgoing.quickreply.QuickReply;
import com.botscrew.bothack.service.SendMessageService;

@Service("consoleService")
public class ConsoleSender implements SendMessageService {

	@Override
	public void sendSimpleMessage(UserId recipient, String text) {
		System.out.println("text: " + text);
	}

	@Override
	public void sendGenericMessages(UserId recipient, List<MessageElement> elements) {
		System.out.println(elements);
	}

	@Override
	public void sendButtonsMessage(UserId recipient, List<Button> buttons, String text) {
		System.out.println("text: " + text + " " + buttons.toString());
	}

	@Override
	public void sendQuickRepliesMessage(UserId recipient, List<QuickReply> quickReplies, String text) {
		System.out.println(text + " " + quickReplies);

	}

	@Override
	public void sendListTemplate(UserId recipient, List<ListTemplateMessageElement> elements) {
		System.out.println(elements);
	}

	@Override
	public void sendDomainWhiteList(DomainWhiteList domainWhiteList) {
		System.out.println(domainWhiteList.toString());
	}
}
