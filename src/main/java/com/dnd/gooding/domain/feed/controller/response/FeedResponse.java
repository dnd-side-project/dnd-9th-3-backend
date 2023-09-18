package com.dnd.gooding.domain.feed.controller.response;

import static java.util.stream.Collectors.*;

import java.util.List;

import com.dnd.gooding.domain.file.controller.response.RecordFileResponse;
import com.dnd.gooding.domain.record.domain.Record;
import com.dnd.gooding.domain.user.controller.response.UserResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "피드 정보")
@Getter
@Builder
public class FeedResponse {

	private Long recordId;
	private String title;
	private String description;
	private String placeTitle;
	private Double placeLatitude;
	private Double placeLongitude;
	private String thumbnailUrl;
	private String interestType;
	private UserResponse user;
	private List<RecordFileResponse> files;

	public static FeedResponse from(Record record) {
		return FeedResponse.builder()
			.recordId(record.getId())
			.title(record.getTitle())
			.description(record.getDescription())
			.placeTitle(record.getPlaceTitle())
			.placeLatitude(record.getPlaceLatitude())
			.placeLongitude(record.getPlaceLongitude())
			.thumbnailUrl(record.getThumbnailUrl())
			.interestType(record.getInterestType().getInterestName())
			.user(UserResponse.from(record.getUser()))
			.files(record.getFiles().stream().map(RecordFileResponse::from).collect(toList()))
			.build();
	}
}
