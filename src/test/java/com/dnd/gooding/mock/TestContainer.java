package com.dnd.gooding.mock;

import com.dnd.gooding.domain.user.controller.UserController;
import com.dnd.gooding.domain.user.service.UserServiceImpl;
import com.dnd.gooding.domain.user.service.port.UserRepository;

import lombok.Builder;

public class TestContainer {

	public final UserRepository userRepository;
	public final UserController userController;

	@Builder
	public TestContainer() {
		this.userRepository = new FakeUserRepository();
		UserServiceImpl userService = UserServiceImpl.builder()
			.userRepository(userRepository)
			.build();
		this.userController = UserController.builder()
			.userService(userService)
			.build();
	}
}
