package com.dnd.gooding.user.command.domain.repository;

import com.dnd.gooding.user.command.domain.Member;
import com.dnd.gooding.user.command.domain.MemberId;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface MemberRepository extends Repository<Member, MemberId> {

    Optional<Member> findById(MemberId memberId);

    void save(Member member);
}
