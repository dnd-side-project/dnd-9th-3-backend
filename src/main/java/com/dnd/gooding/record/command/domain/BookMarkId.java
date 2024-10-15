package com.dnd.gooding.record.command.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class BookMarkId implements Serializable {

    @Column(name = "member_id")
    private String memberId;

    @Column(name = "record_number")
    private String recordNo;

    public BookMarkId(String memberId, String recordNo) {
        this.memberId = memberId;
        this.recordNo = recordNo;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public static BookMarkId of(String memberId, String recordNo) {
        return new BookMarkId(memberId, recordNo);
    }
}
