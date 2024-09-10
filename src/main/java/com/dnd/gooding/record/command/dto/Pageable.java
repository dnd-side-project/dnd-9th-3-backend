package com.dnd.gooding.record.command.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pageable<T> {

    private boolean isEnd;
    private int totalCount;
    private T data;
}
