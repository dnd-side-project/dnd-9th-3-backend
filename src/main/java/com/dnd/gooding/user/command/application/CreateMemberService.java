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

	private MemberRepository memberRepository;

	public CreateMemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public void createMember(String id, String oAuthId) {
		MemberId memberId = MemberId.of(id);
		Member member = new Member(memberId, null, null, null,
			UserRole.ROLE_USER.name(), new OAuthId(oAuthId));
		save(member);
	}

	@Transactional
	public void updateMember(MemberRequest memberRequest) {
		Member member = memberRepository.findById(new MemberId(memberRequest.getId()))
			.orElseThrow(NoMemberException::new);
		member.changeName(memberRequest.getName());
		member.changeInterests(memberRequest.getInterests());
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
