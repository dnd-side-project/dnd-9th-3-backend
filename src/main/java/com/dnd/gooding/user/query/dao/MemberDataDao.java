package com.dnd.gooding.user.query.dao;

import java.util.List;
import java.util.Optional;

import com.dnd.gooding.user.query.dto.MemberData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.Repository;

public interface MemberDataDao extends Repository<MemberData, String> {

	Optional<MemberData> findById(String memberId);

	List<MemberData> findAll(Specification<MemberData> spec, Pageable pageable);
}
