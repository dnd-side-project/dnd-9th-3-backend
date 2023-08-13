package com.dnd.gooding.domain.record.dto.response;

import java.time.LocalDateTime;

import com.dnd.gooding.domain.record.model.Record;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "기록 정보")
public class UploadResponse {

	Long id;
	String title;
	String description;
	LocalDateTime recordDate;
	String placeTitle;
	Double placeLatitude;
	Double placeLongitude;
	String recordOpen;
	int recordScore;

	public static UploadResponse from(Record record) {
		return UploadResponse.builder()
			.id(record.getId())
			.title(record.getTitle())
			.description(record.getDescription())
			.recordDate(record.getRecordDate())
			.placeTitle(record.getPlaceTitle())
			.placeLatitude(record.getPlaceLatitude())
			.placeLongitude(record.getPlaceLongitude())
			.recordOpen(record.getRecordOpen().name())
			.recordScore(record.getRecordScore())
			.build();
	}
}
