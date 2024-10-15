package com.dnd.gooding.record.command.domain;

import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
public class RecordNo implements Serializable {

    @Column(name = "record_number")
    private String number;

    protected RecordNo() {}

    public RecordNo(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecordNo recordNo = (RecordNo) o;
        return Objects.equals(number, recordNo.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    public static RecordNo of(String number) {
        return new RecordNo(number);
    }
}
