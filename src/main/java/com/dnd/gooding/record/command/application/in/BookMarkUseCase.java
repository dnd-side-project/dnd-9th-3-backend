package com.dnd.gooding.record.command.application.in;

public interface BookMarkUseCase {

    String getBookMarkYn(String memberId, String recordNo);
    String bookMark(String memberId, String recordNo);
}
