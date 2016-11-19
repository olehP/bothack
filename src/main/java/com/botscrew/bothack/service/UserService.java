package com.botscrew.bothack.service;

import com.botscrew.bothack.entity.User;
import com.botscrew.bothack.model.UserId;

public interface UserService {

	User createIfNotExist(UserId chatId);

}
