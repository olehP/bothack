package com.botscrew.bothack.service.fake;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.botscrew.bothack.model.UserId;
import com.botscrew.bothack.model.incomming.Entry;
import com.botscrew.bothack.model.incomming.Message;
import com.botscrew.bothack.model.incomming.MessageRecieved;
import com.botscrew.bothack.model.incomming.Messaging;
import com.botscrew.bothack.model.incomming.Postback;
import com.botscrew.bothack.processor.MessagesProcessor;

@Component
public class ConsoleController {
	private static final Scanner SCANNER = new Scanner(System.in);
	private static final String CHAT_ID = "1";// put here your user's chat_id

	@Autowired
	private MessagesProcessor messagesProcessor;

	public void run() {
		while (true) {
			String message = SCANNER.nextLine();
			Messaging messaging = new Messaging();
			messaging.setSender(new UserId(CHAT_ID));
			if (message.startsWith("p:")) {
				Postback postback = new Postback();
				postback.setPayload(message.substring(message.indexOf(':') + 1));
				messaging.setPostback(postback);
			} else {
				Message messageModel = new Message();
				messageModel.setText(message);
				messaging.setMessage(messageModel);
			}
			List<Messaging> messagings = new ArrayList<>();
			messagings.add(messaging);
			Entry entry = new Entry();
			entry.setMessaging(messagings);
			List<Entry> entries = new ArrayList<>();
			entries.add(entry);
			MessageRecieved messageRecieved = new MessageRecieved();
			messageRecieved.setEntry(entries);
			messagesProcessor.processMessage(messageRecieved);
		}
	}
}
