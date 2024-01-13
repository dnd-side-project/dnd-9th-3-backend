package com.dnd.gooding.user.query;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.Repository;

public interface MemberDataDao extends Repository<MemberData, String> {

	MemberData findById(String memberId);

	List<MemberData> findAll(Specification<MemberData> spec, Pageable pageable);
}
