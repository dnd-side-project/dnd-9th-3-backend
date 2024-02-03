package com.dnd.gooding.integration.infra;

import org.springframework.stereotype.Component;

import com.dnd.gooding.integration.OffsetStore;

@Component
public class MemoryOffsetStore implements OffsetStore {

	private long nextOffset = 0;

	@Override
	public long get() {
		return nextOffset;
	}

	@Override
	public void update(long nextOffset) {
		this.nextOffset = nextOffset;
	}
}
