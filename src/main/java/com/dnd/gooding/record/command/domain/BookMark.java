package com.dnd.gooding.record.command.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "bookmark")
@Access(AccessType.FIELD)
@NoArgsConstructor
public class BookMark {

    @EmbeddedId private BookMarkId bookMarkId;

    @Column(name = "bookmark_yn")
    private String bookmarkYn;

    @Builder
    public BookMark(BookMarkId bookMarkId, String bookmarkYn) {
        this.bookMarkId = bookMarkId;
        this.bookmarkYn = bookmarkYn;
    }

    public void changeBookmarkYn() {
        if (this.bookmarkYn == null || this.bookmarkYn.isEmpty()) {
            this.bookmarkYn = "Y";
        } else if ("Y".equals(this.bookmarkYn)) {
            this.bookmarkYn = "N";
        } else {
            this.bookmarkYn = "Y";
        }
    }
}
