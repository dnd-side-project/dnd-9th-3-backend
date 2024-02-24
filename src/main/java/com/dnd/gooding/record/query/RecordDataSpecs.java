package com.dnd.gooding.record.query;

import org.springframework.data.jpa.domain.Specification;

public class RecordDataSpecs {

    private RecordDataSpecs() {}

    public static Specification<RecordData> equalsRecorderId(String recorderId) {
        return (root, query, cb) -> cb.equal(root.get("recorderId"), recorderId);
    }
}
