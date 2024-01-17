package com.dnd.gooding.user.command.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.gooding.common.model.UserRole;
import com.dnd.gooding.oauth.command.domain.OAuthId;
import com.dnd.gooding.user.command.domain.Member;
import com.dnd.gooding.user.command.domain.MemberId;
import com.dnd.gooding.user.command.domain.MemberRepository;

@Service
public class CreateMemberService {

	public MemberRepository memberRepository;

	public CreateMemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public Member createMember(String id, OAuthId oAuthId) {
		MemberId memberId = MemberId.of(id);
		Member member = new Member(memberId, null, null, null,
				UserRole.ROLE_USER.name(), oAuthId);
		save(member);
		return member;
	}

	@Transactional
	public void save(Member member) {
		memberRepository.findById(member.getId()).ifPresentOrElse(
			x -> {
			},
			() -> {
				memberRepository.save(member);
			}
		);
	}
}
