package com.dnd.gooding.common.jpa;

import static java.util.stream.Collectors.*;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.AttributeConverter;

import com.dnd.gooding.common.model.Interest;
import com.dnd.gooding.common.model.InterestSet;
import com.dnd.gooding.common.model.InterestType;

public class InterestConverter implements AttributeConverter<InterestSet, String> {
	@Override
	public String convertToDatabaseColumn(InterestSet attribute) {
		if (attribute == null) {
			return null;
		}
		return attribute.getInterests().stream()
			.map(Interest::getInterestCode)
			.collect(Collectors.joining(","));
	}

	@Override
	public InterestSet convertToEntityAttribute(String dbData) {
		if (dbData == null) {
			return null;
		}
		String[] interests = dbData.split(",");
		Set<Interest> interestSet = Arrays.stream(interests)
			.map(interest -> new Interest(interest, InterestType.of(interest).getInterestName()))
			.collect(toSet());
		return new InterestSet(interestSet);
	}
}
