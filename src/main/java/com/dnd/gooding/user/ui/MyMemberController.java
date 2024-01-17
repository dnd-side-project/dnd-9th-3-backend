package com.dnd.gooding.user.ui;

import com.dnd.gooding.token.command.model.JwtAuthentication;
import com.dnd.gooding.user.query.MemberData;
import com.dnd.gooding.user.query.MemberQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/my/member")
public class MyMemberController {

    private MemberQueryService memberQueryService;

    public MyMemberController(MemberQueryService memberQueryService) {
        this.memberQueryService = memberQueryService;
    }

    @GetMapping
    public ResponseEntity<MemberData> member(
            Authentication authentication) {
        JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication.getPrincipal();
        return ResponseEntity
            .ok()
            .body(memberQueryService.getMember(jwtAuthentication.getMemberId()));
    }
}
