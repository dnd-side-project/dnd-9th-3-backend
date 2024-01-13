package com.dnd.gooding.member.query;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.jdbc.Sql;

import com.dnd.gooding.user.query.MemberData;
import com.dnd.gooding.user.query.MemberDataDao;
import com.dnd.gooding.user.query.MemberDataSpecs;

@SpringBootTest
@Sql("classpath:init-test.sql")
public class MemberDataDaoTest {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private MemberDataDao memberDataDao;

	@Test
	void findById() {
		MemberData result = memberDataDao.findById("yonog7317");
		logger.info("spec result: {}, {}", result.getId(), result.getName());
	}

	@Test
	void findAll() {
		Specification<MemberData> spec = MemberDataSpecs.nameLike("1");
		List<MemberData> result = memberDataDao.findAll(spec, PageRequest.of(1, 2));
		logger.info("spec result: {}", result.size());
	}
}
