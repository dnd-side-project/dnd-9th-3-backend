package com.dnd.gooding.domain.file.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dnd.gooding.domain.file.dto.FileCreate;
import com.dnd.gooding.domain.record.model.Record;
import com.dnd.gooding.domain.user.model.User;
import com.dnd.gooding.global.common.model.BaseEntity;

import lombok.Getter;

@Entity
@Table(name = "file")
@Getter
public class File extends BaseEntity {

	@Id
	@Column(name = "file_id")
	@GeneratedValue
	private Long id;

	@Column(name = "extension", nullable = false, length = 20)
	private String extension;

	@Column(name = "file_url", nullable = false, length = 100)
	private String fileUrl;

	@Column(name = "origin_name", nullable = false, length = 100)
	private String originName;

	@Column(name = "new_name", nullable = false, length = 100)
	private String newName;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
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

	public static File create(FileCreate fileCreate, Record record) {
		File file = new File();
		file.extension = fileCreate.getExtension();
		file.fileUrl = fileCreate.getFileUrl();
		file.originName = fileCreate.getOriginName();
		file.newName = fileCreate.getNewName();
		file.setUser(record.getUser());
		file.setRecord(record);
		return file;
	}
}
