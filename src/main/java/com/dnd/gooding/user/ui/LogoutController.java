package com.dnd.gooding.user.ui;

import com.dnd.gooding.token.command.application.TokenService;
import com.dnd.gooding.util.CookieUtil;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/logout")
public class LogoutController {

    private final TokenService tokenService;

    public LogoutController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @DeleteMapping
    public ResponseEntity<Void> logout(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String refreshToken =
                CookieUtil.getCookie(request, "refreshToken").map(Cookie::getValue).orElseThrow();
        tokenService.deleteRefreshToken(refreshToken);
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, authentication);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
