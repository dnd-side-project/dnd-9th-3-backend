package com.dnd.gooding.domain.onboard.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.gooding.domain.onboard.controller.port.OnboardService;
import com.dnd.gooding.domain.onboard.domain.Onboard;
import com.dnd.gooding.domain.onboard.service.port.OnboardRepository;
import com.dnd.gooding.domain.user.controller.port.UserService;
import com.dnd.gooding.domain.user.controller.response.UserResponse;
import com.dnd.gooding.domain.user.domain.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OnboardServiceImpl implements OnboardService {

	private final UserService userService;
	private final OnboardRepository onboardRepository;

	@Transactional
	@Override
	public UserResponse findByUserIdAndUpdate(Long userId, String nickName, List<String> interestCodes) {
		User user = userService.findByUserId(userId);

		for(String interestCode : interestCodes) {
			Onboard onboard = onboardRepository.save(Onboard.create(user, interestCode));
			user = user.changeOnboard(onboard);
		}
		user = user.changeNickName(nickName);
		user = user.changeOnboardYn("Y");
		user = userService.save(user);
		return UserResponse.from(user);
	}
}
