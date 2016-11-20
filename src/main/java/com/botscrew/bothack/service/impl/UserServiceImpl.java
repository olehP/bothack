package com.botscrew.bothack.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.botscrew.bothack.dao.UserDAO;
import com.botscrew.bothack.entity.ChatState;
import com.botscrew.bothack.entity.Gender;
import com.botscrew.bothack.entity.User;
import com.botscrew.bothack.messages.Style;
import com.botscrew.bothack.model.UserId;
import com.botscrew.bothack.model.incomming.ProfileInfo;
import com.botscrew.bothack.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	private static final String MALE = "male";
	private static final String FEMALE = "female";

	@Value("${facebook.profile.url}")
	private String profileUrl;
	@Value("${facebook.token}")
	private String token;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public User createIfNotExist(UserId chatId) {
		User user = userDAO.findByChatId(chatId.getId());
		if (user == null) {
			user = new User();
			user.setChatId(chatId.getId());
			ProfileInfo profileInfo = getProfileInfo(chatId.getId());
			user.setFirstName(profileInfo.getFirstName());
			user.setLastName(profileInfo.getLastName());
			user.setTimezone(profileInfo.getTimezone());
			if (MALE.equals(profileInfo.getGender())) {
				user.setGender(Gender.MALE);
			} else if (FEMALE.equals(profileInfo.getGender())) {
				user.setGender(Gender.FEMALE);
			}
			user.setStyle(Style.NORMAL);
			user.setChatState(ChatState.START);
			userDAO.save(user);
		}
		return user;
	}

	private ProfileInfo getProfileInfo(String userId) {
		String url = profileUrl.replace("$user_id$", userId) + token;
		return restTemplate.getForObject(url, ProfileInfo.class);
	}
}
