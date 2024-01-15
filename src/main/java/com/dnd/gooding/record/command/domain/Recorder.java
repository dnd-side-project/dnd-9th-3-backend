package com.dnd.gooding.record.command.domain;

import java.util.Objects;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.dnd.gooding.user.command.domain.MemberId;

@Embeddable
public class Recorder {

	@AttributeOverrides(
		@AttributeOverride(name = "id", column = @Column(name = "recorder_id"))
	)
	private MemberId memberId;
	private String name;

	protected Recorder() {
	}

	public Recorder(MemberId memberId, String name) {
		this.memberId = memberId;
		this.name = name;
	}

	public MemberId getMemberId() {
		return memberId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Recorder recorder = (Recorder)o;
		return Objects.equals(memberId, recorder.memberId) && Objects.equals(name, recorder.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(memberId, name);
	}
}
