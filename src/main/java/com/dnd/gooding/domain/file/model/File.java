package com.dnd.gooding.domain.file.model;

import javax.persistence.*;

import com.dnd.gooding.domain.file.dto.FileCreate;
import com.dnd.gooding.domain.record.model.Record;
import com.dnd.gooding.domain.user.model.User;
import com.dnd.gooding.global.common.model.BaseEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class File extends BaseEntity {

	@Id
	@Column(name = "file_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "extension", nullable = false, length = 20)
	private String extension;

	@Column(name = "file_url", nullable = false)
	private String fileUrl;

	@Column(name = "thumbnail_url")
	private String thumbnailUrl;

	@Column(name = "file_size", nullable = false)
	private Long fileSize;

	@Column(name = "origin_name", nullable = false, length = 100)
	private String originName;

	@Column(name = "new_name", nullable = false, length = 100)
	private String newName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "record_id")
	private Record record;

	//==연관관계 메서드==//
	private void setUser(User user) {
		this.user = user;
		user.getFiles().add(this);
	}
	private void setRecord(Record record) {
		this.record = record;
		record.getFiles().add(this);
	}

	public static File create(FileCreate fileCreate, User user) {
		File file = new File();
		file.extension = fileCreate.getExtension();
		file.fileUrl = fileCreate.getFileUrl();
		file.thumbnailUrl = fileCreate.getThumbnailUrl();
		file.fileSize = fileCreate.getFileSize();
		file.originName = fileCreate.getOriginName();
		file.newName = fileCreate.getNewName();
		file.setUser(user);
		return file;
	}

	public static File create(FileCreate fileCreate, Record record) {
		File file = new File();
		file.extension = fileCreate.getExtension();
		file.fileUrl = fileCreate.getFileUrl();
		file.thumbnailUrl = fileCreate.getThumbnailUrl();
		file.fileSize = fileCreate.getFileSize();
		file.originName = fileCreate.getOriginName();
		file.newName = fileCreate.getNewName();
		file.setUser(record.getUser());
		file.setRecord(record);
		return file;
	}
}
