package com.dnd.gooding.domain.onboard.controller.port;

import java.util.List;

import com.dnd.gooding.domain.user.controller.response.UserResponse;

public interface OnboardService {
	UserResponse findByUserIdAndUpdate(Long userId, String nickName, List<String> interestCodes);
}
