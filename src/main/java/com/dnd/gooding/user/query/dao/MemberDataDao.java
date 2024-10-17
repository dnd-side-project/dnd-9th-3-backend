package com.dnd.gooding.user.query.dao;

import com.dnd.gooding.user.query.dto.MemberData;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface MemberDataDao extends Repository<MemberData, String> {

    Optional<MemberData> findById(String memberId);
}
