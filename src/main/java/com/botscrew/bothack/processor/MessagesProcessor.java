package com.botscrew.bothack.processor;

import org.springframework.scheduling.annotation.Async;

import com.botscrew.bothack.model.incomming.MessageRecieved;

public interface MessagesProcessor {
	@Async
	void processMessage(MessageRecieved message);
}
