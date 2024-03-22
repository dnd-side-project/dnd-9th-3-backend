package com.dnd.gooding.record.command.domain;

import com.dnd.gooding.user.command.domain.MemberId;
import java.util.Objects;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Builder;

@Embeddable
public class Recorder {

    @AttributeOverrides(@AttributeOverride(name = "id", column = @Column(name = "recorder_id")))
    private MemberId memberId;

    @Column(name = "recorder_name")
    private String memberName;

    protected Recorder() {}

    @Builder
    public Recorder(MemberId memberId, String memberName) {
        this.memberId = memberId;
        this.memberName = memberName;
    }

    public MemberId getMemberId() {
        return memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recorder recorder = (Recorder) o;
        return Objects.equals(memberId, recorder.memberId)
                && Objects.equals(memberName, recorder.memberName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, memberName);
    }
}
