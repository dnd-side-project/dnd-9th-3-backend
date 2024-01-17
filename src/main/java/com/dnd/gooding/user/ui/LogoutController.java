package com.dnd.gooding.user.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.gooding.token.command.application.TokenService;

@RestController
@RequestMapping("/api/v1/logout")
public class LogoutController {

	@Autowired
	private TokenService tokenService;

	@DeleteMapping
	public ResponseEntity<Void> logout(
		HttpServletRequest request,
		HttpServletResponse response,
		@CookieValue("refreshToken") String refreshToken,
		Authentication authentication) {
		tokenService.deleteRefreshToken(refreshToken);
		SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
		logoutHandler.logout(request, response, authentication);
		return ResponseEntity
			.status(HttpStatus.NO_CONTENT)
			.build();
	}
}
