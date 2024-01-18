package com.dnd.gooding.user.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.gooding.user.command.application.CreateMemberService;
import com.dnd.gooding.user.query.MemberData;
import com.dnd.gooding.user.query.MemberQueryService;

@RestController
@RequestMapping("/api/v1/member")
public class MemberController {

	private MemberQueryService memberQueryService;

	public MemberController(
		MemberQueryService memberQueryService,
		CreateMemberService createMemberService) {
		this.memberQueryService = memberQueryService;
	}

	@GetMapping(value = "/{memberId}")
	public ResponseEntity<MemberData> member(
		@PathVariable String memberId) {
		return ResponseEntity
			.ok()
			.body(memberQueryService.getMember(memberId));
	}
}
