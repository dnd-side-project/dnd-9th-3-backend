package com.dnd.gooding.record.query;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@SpringBootTest
@Sql("classpath:init-test.sql")
public class RecordDataDaoTest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private RecordDataDao recordDataDao;

    @Test
    void findById() {
        RecordData result = recordDataDao.findById("record1");
        logger.info("spec result: {}, {}", result.getRecordNumber(), result.getRecorderId());
    }

    @Test
    void findAll() {
        Specification<RecordData> spec = RecordDataSpecs.equalsRecorderId("yong7317");
        List<RecordData> result = recordDataDao.findAll(spec);
        logger.info("spec result: {}", result.size());
    }
}
