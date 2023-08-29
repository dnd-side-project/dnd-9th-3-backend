package com.dnd.gooding.domain.onboarding.service;

import com.dnd.gooding.domain.onboarding.model.Onboarding;
import com.dnd.gooding.domain.onboarding.repository.OnboardingRepository;
import com.dnd.gooding.domain.user.dto.response.UserProfileResponse;
import com.dnd.gooding.domain.user.model.User;
import com.dnd.gooding.domain.user.controller.port.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OnboardingServiceImpl implements OnboardingService {

    private final UserService userService;
    private final OnboardingRepository onboardingRepository;

    @Transactional
    @Override
    public UserProfileResponse findByUserIdAndUpdate(Long userId, String nickName,
        List<String> interestCodes) {
        User user = userService.findByUserId(userId);

        for(String interestCode : interestCodes) {
            Onboarding onboarding = Onboarding.from(user, interestCode);
            onboardingRepository.insert(onboarding);
        }
        user.changeNickname(nickName);
        // user.changeProfileImgUrl(profileImageUrl);
        user.changeOnboardYn("Y");
        return UserProfileResponse.from(user);
    }
}
