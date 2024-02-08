package com.dnd.gooding.user.ui;

import com.dnd.gooding.token.command.model.JwtAuthentication;
import com.dnd.gooding.user.command.application.CreateMemberService;
import com.dnd.gooding.user.command.application.MemberRequest;
import com.dnd.gooding.user.query.application.MemberQueryService;
import com.dnd.gooding.user.query.dto.MemberData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/my/member")
public class MyMemberController {

    private MemberQueryService memberQueryService;
    private CreateMemberService createMemberService;

    public MyMemberController(
            MemberQueryService memberQueryService, CreateMemberService createMemberService) {
        this.memberQueryService = memberQueryService;
        this.createMemberService = createMemberService;
    }

    @GetMapping
    public ResponseEntity<MemberData> member(Authentication authentication) {
        JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication.getPrincipal();
        return ResponseEntity.ok().body(memberQueryService.getMember(jwtAuthentication.getId()));
    }

    @PostMapping
    public ResponseEntity<Void> member(@RequestBody MemberRequest memberRequest) {
        createMemberService.updateMember(memberRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
