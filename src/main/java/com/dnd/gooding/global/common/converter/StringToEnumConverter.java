package com.dnd.gooding.global.common.converter;

import org.springframework.core.convert.converter.Converter;

import com.dnd.gooding.global.common.enums.InterestType;

public class StringToEnumConverter implements Converter<String, InterestType> {

	@Override
	public InterestType convert(String source) {
		return InterestType.ofInterestCode(source);
	}
}
