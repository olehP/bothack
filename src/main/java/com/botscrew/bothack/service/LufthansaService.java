package com.botscrew.bothack.service;

public interface LufthansaService {

	int getBalance(String userAlias);

	void earnMiles(String userAlias, int miles);

}
