package com.botscrew.bothack.service;

import java.util.List;

import com.botscrew.bothack.model.Button;
import com.botscrew.bothack.model.UserId;
import com.botscrew.bothack.model.outgoing.DomainWhiteList;
import com.botscrew.bothack.model.outgoing.generic.ListTemplateMessageElement;
import com.botscrew.bothack.model.outgoing.generic.MessageElement;
import com.botscrew.bothack.model.outgoing.quickreply.QuickReply;

public interface SendMessageService {
	void sendSimpleMessage(UserId recipient, String text);

	void sendGenericMessages(UserId recipient, List<MessageElement> elements);

	void sendButtonsMessage(UserId recipient, List<Button> buttons, String text);

	void sendQuickRepliesMessage(UserId recipient, List<QuickReply> quickReplies, String text);

	void sendListTemplate(UserId recipient, List<ListTemplateMessageElement> elements);

	void sendDomainWhiteList(DomainWhiteList domainWhiteList);
}
