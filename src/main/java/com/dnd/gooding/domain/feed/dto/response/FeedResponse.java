package com.dnd.gooding.domain.feed.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import com.dnd.gooding.domain.file.dto.response.RecordFileResponse;
import com.dnd.gooding.domain.record.model.Record;
import com.dnd.gooding.domain.user.dto.response.UserProfileResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedResponse {

	private Long recordId;
	private String title;
	private String description;
	private String placeTitle;
	private Double placeLatitude;
	private Double placeLongitude;
	private String interestType;
	private UserProfileResponse user;
	private List<RecordFileResponse> files;

	public FeedResponse(Record record) {
		this.recordId = record.getId();
		this.title = record.getTitle();
		this.description = record.getDescription();
		this.placeTitle = record.getPlaceTitle();
		this.placeLatitude = record.getPlaceLatitude();
		this.placeLongitude = record.getPlaceLongitude();
		this.interestType = record.getInterestType().getInterestName();
		this.user = UserProfileResponse.from(record.getUser());
		this.files = record.getFiles().stream()
			.map(RecordFileResponse::new)
			.collect(Collectors.toList());
	}
}
