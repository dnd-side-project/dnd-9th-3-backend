package com.dnd.gooding.domain.onboarding.controller;

import com.dnd.gooding.domain.onboarding.service.OnboardingService;
import com.dnd.gooding.domain.user.dto.response.UserProfileResponse;
import com.dnd.gooding.global.common.dto.ErrorResponse;
import com.dnd.gooding.global.common.model.InterestType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "Onboard", description = " 온보딩 API")
@RestController
@RequestMapping("/api/v1/onboard")
@RequiredArgsConstructor
public class OnboardingController {

    private final OnboardingService onboardingService;

    @Operation(summary = "온보딩 내용을 저장한다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "정상처리"),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자입니다.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping(value = "/update/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfileResponse> update(
            @PathVariable("userId") Long userId,
            @RequestPart("nickName") String nickName,
            @RequestPart("profileImage") MultipartFile profileImage,
            @RequestPart("onboardings") List<InterestType> onboardings) throws IOException {
        return ResponseEntity
                .ok()
                .body(onboardingService.findByUserIdAndUpdate(userId, nickName, profileImage, onboardings));
    }
}
