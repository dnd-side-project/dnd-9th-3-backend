package com.dnd.gooding.domain.onboarding.service;

import com.dnd.gooding.domain.user.dto.response.UserProfileResponse;
import com.dnd.gooding.global.common.model.InterestType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface OnboardingService {

    UserProfileResponse findByUserIdAndUpdate(Long userId, String nickName, MultipartFile profileImage, List<InterestType> onboardings) throws IOException;
}
