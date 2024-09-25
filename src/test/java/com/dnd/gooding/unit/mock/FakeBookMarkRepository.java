package com.dnd.gooding.unit.mock;

import com.dnd.gooding.record.command.domain.BookMark;
import com.dnd.gooding.record.command.domain.repository.BookMarkRepository;

public class FakeBookMarkRepository implements BookMarkRepository {

    @Override
    public void save(BookMark bookMark) {}
}
