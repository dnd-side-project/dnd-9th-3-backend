package com.dnd.gooding.domain.record.domain;

import java.time.LocalDateTime;
import java.util.List;

import com.dnd.gooding.domain.file.domain.File;
import com.dnd.gooding.domain.record.controller.request.UploadRequest;
import com.dnd.gooding.domain.user.domain.User;
import com.dnd.gooding.global.common.enums.InterestType;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Record {
	private final Long id;
	private final String title;
	private final String description;
	private final LocalDateTime recordDate;
	private final String placeTitle;
	private final Double placeLatitude;
	private final Double placeLongitude;
	private final RecordOpenStatus recordOpen;
	private final Integer recordScore;
	private final User user;
	private final String thumbnailUrl;
	private final InterestType interestType;
	private List<File> files;

	@Builder
	public Record(Long id, String title, String description, LocalDateTime recordDate, String placeTitle,
		Double placeLatitude, Double placeLongitude, RecordOpenStatus recordOpen, Integer recordScore, User user,
		String thumbnailUrl, InterestType interestType, List<File> files) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.recordDate = recordDate;
		this.placeTitle = placeTitle;
		this.placeLatitude = placeLatitude;
		this.placeLongitude = placeLongitude;
		this.recordOpen = recordOpen;
		this.recordScore = recordScore;
		this.user = user;
		this.thumbnailUrl = thumbnailUrl;
		this.interestType = interestType;
		this.files = files;
	}

	public static Record create(UploadRequest uploadRequest, User user) {
		return Record.builder()
			.title(uploadRequest.getTitle())
			.description(uploadRequest.getDescription())
			.recordDate(uploadRequest.getRecordDate())
			.placeTitle(uploadRequest.getPlaceTitle())
			.placeLatitude(uploadRequest.getPlaceLatitude())
			.placeLongitude(uploadRequest.getPlaceLongitude())
			.recordOpen(uploadRequest.getRecordOpen())
			.recordScore(uploadRequest.getRecordScore())
			.interestType(uploadRequest.getInterestType())
			.user(user)
			.build();
	}

	public List<File> addFile(File file) {
		files.add(file);
		return files;
	}

	public Record changeFile(File file) {
		return Record.builder()
			.id(id)
			.title(title)
			.description(description)
			.recordDate(recordDate)
			.placeTitle(placeTitle)
			.placeLatitude(placeLatitude)
			.placeLongitude(placeLongitude)
			.recordOpen(recordOpen)
			.recordScore(recordScore)
			.user(user)
			.thumbnailUrl(thumbnailUrl)
			.interestType(interestType)
			.files(addFile(file))
			.build();
	}

	public Record changeThumbnailUrl(String thumbnailUrl) {
		return Record.builder()
			.id(id)
			.title(title)
			.description(description)
			.recordDate(recordDate)
			.placeTitle(placeTitle)
			.placeLatitude(placeLatitude)
			.placeLongitude(placeLongitude)
			.recordOpen(recordOpen)
			.recordScore(recordScore)
			.user(user)
			.thumbnailUrl(thumbnailUrl)
			.interestType(interestType)
			.files(files)
			.build();
	}
}
