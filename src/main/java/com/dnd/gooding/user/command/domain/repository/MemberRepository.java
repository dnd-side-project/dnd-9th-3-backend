package com.dnd.gooding.user.command.domain.repository;

import java.util.Optional;
import org.springframework.data.repository.Repository;

import com.dnd.gooding.user.command.domain.Member;
import com.dnd.gooding.user.command.domain.MemberId;

public interface MemberRepository extends Repository<Member, MemberId> {

    Optional<Member> findById(MemberId memberId);

    void save(Member member);
}
