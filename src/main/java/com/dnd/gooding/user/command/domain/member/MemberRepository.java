package com.dnd.gooding.user.command.domain.member;

import java.util.Optional;

import org.springframework.data.repository.Repository;

public interface MemberRepository extends Repository<Member, MemberId> {

	Optional<Member> findById(MemberId memberId);

	void save(Member member);
}
