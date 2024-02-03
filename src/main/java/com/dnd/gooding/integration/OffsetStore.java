package com.dnd.gooding.integration;

public interface OffsetStore {

	long get();

	void update(long nextOffset);
}
