package com.botscrew.bothack.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.botscrew.bothack.entity.User;

public interface UserDAO extends JpaRepository<User, Integer> {
	User findByChatId(String chatId);
}
