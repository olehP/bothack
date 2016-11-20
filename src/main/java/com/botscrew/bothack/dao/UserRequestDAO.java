package com.botscrew.bothack.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.botscrew.bothack.entity.User;
import com.botscrew.bothack.entity.UserRequest;

public interface UserRequestDAO extends JpaRepository<UserRequest, Integer> {

	UserRequest findByUserAndIsActiveTrue(User user);
}
