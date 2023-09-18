package com.dnd.gooding.mock;

import com.dnd.gooding.domain.user.controller.UserController;
import com.dnd.gooding.domain.user.service.UserServiceImpl;
import com.dnd.gooding.domain.user.service.port.UserRepository;

import lombok.Builder;

public class TestContainer {

	@Builder
	public TestContainer() {
		// UserRepository userRepository = new FakeUserRepository();
		// UserServiceImpl userService = UserServiceImpl.builder()
		// 	.userRepository(userRepository)
		// 	.build();
		// UserController userController = UserController.builder()
		// 	.userService(userService)
		// 	.build();
	}
}
