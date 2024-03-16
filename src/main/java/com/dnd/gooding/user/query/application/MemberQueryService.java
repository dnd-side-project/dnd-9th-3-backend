package com.dnd.gooding.user.query.application;

import com.dnd.gooding.user.exception.NoMemberException;
import com.dnd.gooding.user.query.dao.MemberDataDao;
import com.dnd.gooding.user.query.dto.MemberData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberQueryService {

    private final MemberDataDao memberDataDao;

    public MemberQueryService(MemberDataDao memberDataDao) {
        this.memberDataDao = memberDataDao;
    }

    @Transactional(readOnly = true)
    public MemberData getMember(String id) {
        return memberDataDao.findById(id).orElseThrow(NoMemberException::new);
    }
}
