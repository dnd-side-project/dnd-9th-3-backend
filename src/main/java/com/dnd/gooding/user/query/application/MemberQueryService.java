package com.dnd.gooding.user.query.application;

import com.dnd.gooding.user.command.application.NoMemberException;
import com.dnd.gooding.user.query.dao.MemberDataDao;
import com.dnd.gooding.user.query.dto.MemberData;
import org.springframework.stereotype.Service;

@Service
public class MemberQueryService {

    private MemberDataDao memberDataDao;

    public MemberQueryService(MemberDataDao memberDataDao) {
        this.memberDataDao = memberDataDao;
    }

    public MemberData getMember(String id) {
        return memberDataDao.findById(id).orElseThrow(NoMemberException::new);
    }
}
