package com.dnd.gooding.domain.record.dto;

import java.time.LocalDateTime;

import com.dnd.gooding.domain.record.model.Record;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "기록 정보")
public class RecordResponse {

	Long id;
	String title;
	String description;
	LocalDateTime recordDate;
	String recordPlaceTitle;
	Double recordPlaceLatitude;
	Double recordPlaceLongitude;
	String recordOpen;
	int recordScore;

	public static RecordResponse from(Record record) {
		return RecordResponse.builder()
			.id(record.getId())
			.title(record.getTitle())
			.description(record.getDescription())
			.recordDate(record.getRecordDate())
			.recordPlaceTitle(record.getRecordPlaceTitle())
			.recordPlaceLatitude(record.getRecordPlaceLatitude())
			.recordPlaceLongitude(record.getRecordPlaceLongitude())
			.recordOpen(record.getRecordOpen().name())
			.recordScore(record.getRecordScore())
			.build();
	}
}
