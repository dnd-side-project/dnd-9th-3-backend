package com.dnd.gooding.user.command.domain.service;

import com.dnd.gooding.common.model.UserRole;
import com.dnd.gooding.oauth.command.domain.OAuthId;
import com.dnd.gooding.user.command.application.in.UpdateMemberUseCase;
import com.dnd.gooding.user.ui.dto.request.MemberRequest;
import com.dnd.gooding.user.command.application.in.CreateMemberUseCase;
import com.dnd.gooding.user.command.domain.Member;
import com.dnd.gooding.user.command.domain.MemberId;
import com.dnd.gooding.user.command.domain.repository.MemberRepository;
import com.dnd.gooding.user.exception.NoMemberException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService implements CreateMemberUseCase, UpdateMemberUseCase {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void create(String id, String oAuthId) {
        MemberId memberId = MemberId.of(id);
        Member member =
                Member.builder()
                        .id(memberId)
                        .name(null)
                        .emails(null)
                        .interests(null)
                        .userRole(UserRole.ROLE_USER.name())
                        .oAuthId(new OAuthId(oAuthId))
                        .build();
        save(member);
    }

    @Transactional
    public void updateMember(MemberRequest memberRequest) {
        Member member =
                memberRepository
                        .findById(new MemberId(memberRequest.getId()))
                        .orElseThrow(NoMemberException::new);

        member.changeName(memberRequest.getName());
        member.changeInterests(memberRequest.getInterests());
    }

    private void save(Member member) {
        memberRepository
                .findById(member.getId())
                .ifPresentOrElse(x -> {}, () -> memberRepository.save(member));
    }
}
