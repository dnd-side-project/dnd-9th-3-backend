package com.dnd.gooding.user.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.gooding.user.query.dto.MemberData;
import com.dnd.gooding.user.query.application.MemberQueryService;

@RestController
@RequestMapping("/api/v1/member")
public class MemberController {

	private MemberQueryService memberQueryService;

	public MemberController(
		MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<MemberData> member(
		@PathVariable String id) {
		return ResponseEntity
			.ok()
			.body(memberQueryService.getMember(id));
	}


}
