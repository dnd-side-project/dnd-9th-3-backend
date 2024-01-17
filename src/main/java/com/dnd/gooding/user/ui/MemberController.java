package com.dnd.gooding.user.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.gooding.user.command.application.MemberService;
import com.dnd.gooding.user.query.MemberData;
import com.dnd.gooding.user.query.MemberQueryService;

@RestController
@RequestMapping("/api/v1/member")
public class MemberController {

	private MemberQueryService memberQueryService;
	private MemberService memberService;

	public MemberController(
		MemberQueryService memberQueryService,
		MemberService memberService) {
		this.memberQueryService = memberQueryService;
		this.memberService = memberService;
	}

	@GetMapping(value = "/{memberId}")
	public ResponseEntity<MemberData> member(
		@PathVariable String memberId) {
		return ResponseEntity
			.ok()
			.body(memberQueryService.getMember(memberId));
	}
}
