package com.dnd.gooding.domain.record.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.dnd.gooding.domain.file.model.File;
import com.dnd.gooding.domain.record.dto.request.UploadRequest;
import com.dnd.gooding.domain.user.model.User;
import com.dnd.gooding.global.common.converter.InterestConverter;
import com.dnd.gooding.global.common.model.BaseEntity;
import com.dnd.gooding.global.common.model.InterestType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "record")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Record extends BaseEntity {

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

	@Column(name = "place_title", length = 20)
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
	private User user;

	@Column(name = "thumbnail_url")
	private String thumbnailUrl;

	@Enumerated
	@Convert(converter = InterestConverter.class)
	private InterestType interestType;

	@OneToMany(mappedBy = "record", cascade = CascadeType.ALL)	// 주인이 아닌 쪽에 mappedBy
	private List<File> files = new ArrayList<>();

	//==연관관계 메서드==//
	public void setUser(User user) {
		this.user = user;
		user.getRecords().add(this);
	}

	public static Record create(UploadRequest uploadRequest, User user) {
		Record record = new Record();
		record.title = uploadRequest.getTitle();
		record.description = uploadRequest.getDescription();
		record.recordDate = uploadRequest.getRecordDate();
		record.placeTitle = uploadRequest.getPlaceTitle();
		record.placeLatitude = uploadRequest.getPlaceLatitude();
		record.placeLongitude = uploadRequest.getPlaceLongitude();
		record.recordOpen = uploadRequest.getRecordOpen();
		record.recordScore = uploadRequest.getRecordScore();
		record.interestType = uploadRequest.getInterestType();
		record.setUser(user);
		return record;
	}
}
