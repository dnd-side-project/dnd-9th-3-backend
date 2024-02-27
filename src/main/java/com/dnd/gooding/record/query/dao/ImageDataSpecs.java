package com.dnd.gooding.record.query.dao;

import com.dnd.gooding.record.query.dto.RecordData;
import org.springframework.data.jpa.domain.Specification;

public class ImageDataSpecs {

    private ImageDataSpecs() {}

    public static Specification<RecordData> equalsRecordId(String recordId) {
        return (root, query, cb) -> cb.equal(root.get("recordId"), recordId);
    }
}
