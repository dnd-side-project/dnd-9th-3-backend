package com.dnd.gooding.domain.record.controller.response;

import static java.util.stream.Collectors.*;

import java.time.LocalDateTime;
import java.util.List;

import com.dnd.gooding.domain.file.controller.response.RecordFileResponse;
import com.dnd.gooding.domain.record.domain.Record;
import com.dnd.gooding.domain.record.domain.RecordOpenStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "기록 정보")
@Getter
@Builder
public class MyRecordResponse {
	private Long id;
	private String title;
	private String description;
	private String thumbnailUrl;
	private LocalDateTime recordDate;
	private String placeTitle;
	private Double placeLatitude;
	private Double placeLongitude;
	private Integer recordScore;
	private RecordOpenStatus recordOpen;
	private String interestType;
	private List<RecordFileResponse> files;

	public static MyRecordResponse from(Record record) {
		return MyRecordResponse.builder()
			.id(record.getId())
			.title(record.getTitle())
			.description(record.getDescription())
			.thumbnailUrl(record.getThumbnailUrl())
			.recordDate(record.getRecordDate())
			.placeTitle(record.getPlaceTitle())
			.placeLatitude(record.getPlaceLatitude())
			.placeLongitude(record.getPlaceLongitude())
			.recordScore(record.getRecordScore())
			.recordOpen(record.getRecordOpen())
			.interestType(record.getInterestType().getInterestName())
			.files(record.getFiles().stream()
				.map(RecordFileResponse::from)
				.collect(toList()))
			.build();
	}
}
