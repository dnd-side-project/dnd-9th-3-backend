package com.dnd.gooding.user.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.gooding.user.command.application.CreateMemberService;
import com.dnd.gooding.user.command.application.MemberRequest;
import com.dnd.gooding.user.query.MemberData;
import com.dnd.gooding.user.query.MemberQueryService;

@RestController
@RequestMapping("/api/v1/member")
public class MemberController {

	private MemberQueryService memberQueryService;
	private CreateMemberService createMemberService;

	public MemberController(
		MemberQueryService memberQueryService,
		CreateMemberService createMemberService) {
		this.memberQueryService = memberQueryService;
		this.createMemberService = createMemberService;
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<MemberData> member(
		@PathVariable String id) {
		return ResponseEntity
			.ok()
			.body(memberQueryService.getMember(id));
	}

	@PostMapping
	public ResponseEntity<Void> member(
		@RequestBody MemberRequest memberRequest) {
		createMemberService.updateMember(memberRequest);
		return ResponseEntity
			.status(HttpStatus.NO_CONTENT)
			.build();
	}
}
