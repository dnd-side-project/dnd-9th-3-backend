package com.dnd.gooding.user.ui;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dnd.gooding.token.command.application.in.LogoutTokenUseCase;
import com.dnd.gooding.token.command.domain.dto.JwtAuthentication;
import com.dnd.gooding.user.command.application.in.UpdateMemberUseCase;
import com.dnd.gooding.user.ui.dto.request.MemberRequest;
import com.dnd.gooding.user.query.application.MemberQueryService;
import com.dnd.gooding.user.query.dto.MemberData;
import com.dnd.gooding.util.CookieUtil;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/my/member")
public class MyMemberController {

    private final MemberQueryService memberQueryService;
    private final UpdateMemberUseCase updateMemberUseCase;
    private final LogoutTokenUseCase logoutTokenUseCase;

    public MyMemberController(
        MemberQueryService memberQueryService,
        UpdateMemberUseCase updateMemberUseCase,
        LogoutTokenUseCase logoutTokenUseCase) {
        this.memberQueryService = memberQueryService;
        this.updateMemberUseCase = updateMemberUseCase;
        this.logoutTokenUseCase = logoutTokenUseCase;
    }

    @GetMapping
    public ResponseEntity<MemberData> member(Authentication authentication) {
        JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication.getPrincipal();
        return ResponseEntity.ok().body(memberQueryService.getMember(jwtAuthentication.getId()));
    }

    @PostMapping
    public ResponseEntity<Void> member(@RequestBody MemberRequest memberRequest) {
        updateMemberUseCase.updateMember(memberRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(
        HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String refreshToken =
            CookieUtil.getCookie(request, "refreshToken").map(Cookie::getValue).orElseThrow();
        logoutTokenUseCase.deleteRefreshToken(refreshToken);
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, authentication);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
