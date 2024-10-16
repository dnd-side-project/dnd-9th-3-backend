package com.dnd.gooding.unit.mock;

import com.dnd.gooding.record.command.domain.BookMark;
import com.dnd.gooding.record.command.domain.BookMarkId;
import com.dnd.gooding.record.command.domain.repository.BookMarkRepository;
import java.util.Optional;

public class FakeBookMarkRepository implements BookMarkRepository {

    @Override
    public void save(BookMark bookMark) {}

    @Override
    public Optional<BookMark> findById(BookMarkId bookMarkId) {
        return Optional.empty();
    }
}
