package com.dnd.gooding.domain.record.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.dnd.gooding.domain.file.model.File;
import com.dnd.gooding.domain.user.model.User;
import com.dnd.gooding.global.common.domain.BaseEntity;

import lombok.Getter;

@Entity
@Table(name = "record")
@Getter
public class Record extends BaseEntity {

	@Id
	@Column(name = "record_id")
	@GeneratedValue
	private Long id;

	@Column(name = "title", nullable = false, length = 20)
	private String title;

	@Column(name = "description", nullable = false, length = 100)
	private String description;

	@Column(name = "record_date", nullable = false)
	private LocalDateTime recordDate;

	@Column(name = "place_title", nullable = false, length = 20)
	private String placeTitle;

	@Column(name = "place_latitude", nullable = false)
	private Double placeLatitude;

	@Column(name = "place_longitude", nullable = false)
	private Double placeLongitude;

	@Enumerated(EnumType.STRING)
	@Column(name = "record_open", nullable = false)
	private RecordOpenStatus recordOpen;

	@Column(name = "record_score", nullable = false)
	private int recordScore;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	// @OneToMany(mappedBy = "file")	// 주인이 아닌 쪽에 mappedBy
	// private List<File> files = new ArrayList<>();

	//==연관관계 메서드==//
	public void setUser(User user) {
		this.user = user;
		user.getRecords().add(this);
	}

	//==생성 메서드==//
	public static Record createRecord(User user) {
		Record record = new Record();
		record.setUser(user);
		return record;
	}
}
