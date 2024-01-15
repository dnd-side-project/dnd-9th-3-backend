package com.dnd.gooding.user.command.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.gooding.common.model.EmailSet;
import com.dnd.gooding.common.model.InterestSet;
import com.dnd.gooding.common.model.UserRole;
import com.dnd.gooding.oauth.command.domain.OAuthId;
import com.dnd.gooding.user.command.domain.Member;
import com.dnd.gooding.user.command.domain.MemberId;
import com.dnd.gooding.user.command.domain.MemberRepository;

@Service
public class MemberService {

	public MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Transactional
	public void createMember(MemberRequest memberRequest) {
		MemberId memberId = new MemberId(memberRequest.getMemberId());
		EmailSet emailSet = new EmailSet(memberRequest.getEmails());
		InterestSet interestSet = new InterestSet(memberRequest.getInterests());
		OAuthId oAuthId = new OAuthId(memberRequest.getoAuthId());
		memberRepository.findById(memberId).ifPresentOrElse(
			x -> {
			},
			() -> {
				memberRepository.save(new Member(memberId, memberRequest.getName(),
					emailSet, interestSet, UserRole.ROLE_USER.name(), oAuthId));
			}
		);
	}
}
