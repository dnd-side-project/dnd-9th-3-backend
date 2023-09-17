package com.dnd.gooding.domain.record.infrastructure;

import static java.util.stream.Collectors.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.dnd.gooding.domain.file.infrastructure.FileEntity;
import com.dnd.gooding.domain.record.domain.Record;
import com.dnd.gooding.domain.record.domain.RecordOpenStatus;
import com.dnd.gooding.domain.user.infrastructure.UserEntity;
import com.dnd.gooding.global.common.converter.InterestConverter;
import com.dnd.gooding.global.common.domain.BaseEntity;
import com.dnd.gooding.global.common.enums.InterestType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "record")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecordEntity extends BaseEntity {

	@Id
	@Column(name = "record_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title", nullable = false, length = 20)
	private String title;

	@Column(name = "description", length = 100)
	private String description;

	@Column(name = "record_date", nullable = false)
	private LocalDateTime recordDate;

	@Column(name = "place_title")
	private String placeTitle;

	@Column(name = "place_latitude")
	private Double placeLatitude;

	@Column(name = "place_longitude")
	private Double placeLongitude;

	@Enumerated(EnumType.STRING)
	@Column(name = "record_open", nullable = false)
	private RecordOpenStatus recordOpen;

	@Column(name = "record_score", nullable = false)
	private Integer recordScore;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserEntity userEntity;

	@Column(name = "thumbnail_url")
	private String thumbnailUrl;

	@Column(name = "interest_type")
	@Convert(converter = InterestConverter.class)
	private InterestType interestType;

	@OneToMany(mappedBy = "recordEntity", cascade = CascadeType.ALL)
	private List<FileEntity> files = new ArrayList<>();

	// @OneToMany(mappedBy = "record", cascade = CascadeType.ALL)
	// private List<Feed> feeds = new ArrayList<>();

	public static RecordEntity from(Record record) {
		RecordEntity recordEntity = new RecordEntity();
		recordEntity.id = record.getId();
		recordEntity.title = record.getTitle();
		recordEntity.description = record.getDescription();
		recordEntity.recordDate = record.getRecordDate();
		recordEntity.placeTitle = record.getPlaceTitle();
		recordEntity.placeLatitude = record.getPlaceLatitude();
		recordEntity.placeLongitude = record.getPlaceLongitude();
		recordEntity.recordOpen = record.getRecordOpen();
		recordEntity.recordScore = record.getRecordScore();
		recordEntity.userEntity = UserEntity.from(record.getUser());
		recordEntity.thumbnailUrl = record.getThumbnailUrl();
		recordEntity.interestType = record.getInterestType();
		return recordEntity;
	}

	public static RecordEntity delete(Record record) {
		RecordEntity recordEntity = new RecordEntity();
		recordEntity.id = record.getId();
		recordEntity.title = record.getTitle();
		recordEntity.description = record.getDescription();
		recordEntity.recordDate = record.getRecordDate();
		recordEntity.placeTitle = record.getPlaceTitle();
		recordEntity.placeLatitude = record.getPlaceLatitude();
		recordEntity.placeLongitude = record.getPlaceLongitude();
		recordEntity.recordOpen = record.getRecordOpen();
		recordEntity.recordScore = record.getRecordScore();
		recordEntity.userEntity = UserEntity.from(record.getUser());
		recordEntity.thumbnailUrl = record.getThumbnailUrl();
		recordEntity.interestType = record.getInterestType();
		recordEntity.files = record.getFiles().stream().map(FileEntity::delete).collect(toList());
		return recordEntity;
	}

	public Record toModel() {
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
			.interestType(interestType)
			.user(userEntity.toModel())
			.files(files.stream().map(FileEntity::toDomain).collect(toList()))
			.build();
	}
}
