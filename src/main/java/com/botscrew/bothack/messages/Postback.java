package com.botscrew.bothack.messages;

public enum Postback {
	GET_STARTED("", "GET_STARTED"), MUSIC_CONNECT("", "MUSIC_CONNECT"), LOYALTY_CONNECT("", "LOYALTY_CONNECT"), BUY("",
			"BUY_"), YES("", "YES"), NO("", "NO");
	private String titleKey;
	private String payload;

	public String titleKey() {
		return titleKey;
	}

	public String payload() {
		return payload;
	}

	private Postback(String titleKey, String payload) {
		this.titleKey = titleKey;
		this.payload = payload;
	}
}
