package com.dnd.gooding.common.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class InterestSet {

	private Set<Interest> interests = new HashSet<>();

	public InterestSet(Set<Interest> interests) {
		this.interests.addAll(interests);
	}

	public Set<Interest> getInterests() {
		return Collections.unmodifiableSet(interests);
	}
}
