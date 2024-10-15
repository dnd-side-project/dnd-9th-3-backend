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

    @Transactional(readOnly = true)
    @Override
    public String getBookMarkYn(String memberId, String recordNo) {
        BookMark bookMark = bookMarkRepository.findById(BookMarkId.of(memberId, recordNo)).orElse(new BookMark());
        return bookMark.getBookmarkYn();
    }

    @Transactional
    @Override
    public String bookMark(String memberId, String recordNo) {
        BookMark bookMark = bookMarkRepository.findById(BookMarkId.of(memberId, recordNo)).orElseGet(() -> BookMark.builder()
                .bookMarkId(BookMarkId.of(memberId, recordNo))
                .build());
        bookMark.changeBookmarkYn();
        bookMarkRepository.save(bookMark);
        return bookMark.getBookmarkYn();
    }
}
