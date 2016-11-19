package com.botscrew.bothack.messages;

public enum MessageKey {
	//@formatter:off
	WELCOME("message.welcome");
	//@formatter:on
	private String value;

	public String value() {
		return value;
	}

	private MessageKey(String value) {
		this.value = value;
	}
}
