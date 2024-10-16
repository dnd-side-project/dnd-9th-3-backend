package com.dnd.gooding.user.command.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class MemberId implements Serializable {

    @Column(name = "member_id")
    private String id;

    protected MemberId() {}

    public MemberId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberId memberId = (MemberId) o;
        return Objects.equals(id, memberId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static MemberId of(String id) {
        return new MemberId(id);
    }
}
