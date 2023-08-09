package com.dnd.gooding.domain.file.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dnd.gooding.domain.record.model.Record;
import com.dnd.gooding.domain.user.model.User;
import com.dnd.gooding.global.common.domain.BaseEntity;

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

	@Column(name = "oauth_id", nullable = false)
	private String oauthId;

	@ManyToOne
	@JoinColumn(name = "oauth_id")
	private User user;

	//==연관관계 메서드==//
	public void setUser(User user) {
		this.user = user;
		user.getFiles().add(this);
	}
}
