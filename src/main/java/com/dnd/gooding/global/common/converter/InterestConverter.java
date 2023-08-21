package com.dnd.gooding.global.common.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.dnd.gooding.global.common.model.InterestType;

@Converter
public class InterestConverter implements AttributeConverter<InterestType, String> {

	/**
	 * DB에 저장
	 * @param attribute  the entity attribute value to be converted
	 * @return
	 */
	@Override
	public String convertToDatabaseColumn(InterestType attribute) {
		if (attribute == null) {
			return null;
		}
		return attribute.getInterestCode();
	}

	/**
	 * Entity로 반환
	 * @param dbData  the data from the database column to beconverted
	 * @return
	 */
	@Override
	public InterestType convertToEntityAttribute(String dbData) {
		if (dbData == null) {
			return null;
		}
		return InterestType.ofInterestCode(dbData);
	}
}
