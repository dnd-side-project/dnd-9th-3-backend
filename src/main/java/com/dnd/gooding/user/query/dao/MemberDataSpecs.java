package com.dnd.gooding.user.query.dao;

import com.dnd.gooding.user.query.dto.MemberData;
import org.springframework.data.jpa.domain.Specification;

public class MemberDataSpecs {

    public static Specification<MemberData> nameLike(String keyword) {
        return (root, query, cb) -> cb.like(root.get("name"), "%" + keyword + "%");
    }
}
