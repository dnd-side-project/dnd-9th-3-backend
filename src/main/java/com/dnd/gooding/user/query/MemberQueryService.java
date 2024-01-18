package com.dnd.gooding.user.query;

import org.springframework.stereotype.Service;

import com.dnd.gooding.user.command.application.NoMemberException;

@Service
public class MemberQueryService {

	private MemberDataDao memberDataDao;

	public MemberQueryService(MemberDataDao memberDataDao) {
		this.memberDataDao = memberDataDao;
	}

	public MemberData getMember(String id) {
		return memberDataDao.findById(id)
			.orElseThrow(NoMemberException::new);
	}
}
