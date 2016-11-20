package com.botscrew.bothack.messages;

public enum MessageKey {
	//@formatter:off
	WELCOME("message.welcome"),
	MUSIC("message.music"),
	LOYALTY("message.loyalty"),
	WHAT_TO_DO("message.whattodo"),
	PRERESULT("message.preresult"),
	ANYTHING_ELSE("message.anything.else"),
	PING_REQUEST("message.ping.request"),
	MILES("message.miles"),
	FINAL_PRICE("message.price.final"),
	SHIPPING_ADDRESS("message.address.shipping"),
	LAST("message.last");
	//@formatter:on
	private String value;

	public String value() {
		return value;
	}

	private MessageKey(String value) {
		this.value = value;
	}
}
