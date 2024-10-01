package com.dnd.gooding.record.command.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@Table(name = "bookmark")
@Access(AccessType.FIELD)
public class BookMark {

    @EmbeddedId private BookMarkId bookMarkId;

    @Column(name = "bookmark_yn")
    private String bookmarkYn;

    @Builder
    public BookMark(BookMarkId bookMarkId, String bookmarkYn) {
        this.bookMarkId = bookMarkId;
        this.bookmarkYn = bookmarkYn;
    }
}
