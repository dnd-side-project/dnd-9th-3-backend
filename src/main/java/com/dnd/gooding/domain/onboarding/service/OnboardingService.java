package com.dnd.gooding.domain.onboarding.service;

import com.dnd.gooding.domain.user.dto.response.UserProfileResponse;

import java.util.List;

public interface OnboardingService {

    UserProfileResponse findByUserIdAndUpdate(Long userId, String nickName, List<String> interestCodes);
}
