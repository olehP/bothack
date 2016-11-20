package com.botscrew.bothack.service;

import com.botscrew.bothack.entity.User;
import com.botscrew.bothack.entity.UserRequest;

public interface GeneralResponseService {

	void sendGetStartedMessage(User user);

	void askAboutMusic(User user);

	void askAboutLoyalty(User user);

	void askWhatToDo(User user);

	void sendRecommendations(UserRequest userRequest);

	void sendListResponse(User user);

	void processAnythingElse(User user);

	void sendSearchResults(User user);

	void sendUseMiles(User user);

	void sendFinalPrice(User user);

	void sendYesNo(User user);

}
