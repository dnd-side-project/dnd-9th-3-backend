package com.dnd.gooding.record.command.domain.service;

import com.dnd.gooding.record.command.application.in.BookMarkUseCase;
import com.dnd.gooding.record.command.domain.BookMark;
import com.dnd.gooding.record.command.domain.BookMarkId;
import com.dnd.gooding.record.command.domain.repository.BookMarkRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class BookMarkService implements BookMarkUseCase {

    private final BookMarkRepository bookMarkRepository;

    @Transactional
    @Override
    public void bookMark(String memberId, String recordNo) {
        BookMark mark = BookMark.builder().bookMarkId(BookMarkId.of(memberId, recordNo)).build();
        bookMarkRepository.save(mark);
    }
}
