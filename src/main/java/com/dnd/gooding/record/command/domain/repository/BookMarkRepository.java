package com.dnd.gooding.record.command.domain.repository;

import com.dnd.gooding.record.command.domain.BookMark;
import com.dnd.gooding.record.command.domain.BookMarkId;
import org.springframework.data.repository.Repository;

public interface BookMarkRepository extends Repository<BookMark, BookMarkId> {

    void save(BookMark bookMark);
}
