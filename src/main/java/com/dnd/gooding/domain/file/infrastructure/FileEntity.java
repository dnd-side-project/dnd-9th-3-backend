package com.dnd.gooding.domain.file.infrastructure;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dnd.gooding.domain.user.infrastructure.UserEntity;
import com.dnd.gooding.global.common.domain.BaseEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileEntity extends BaseEntity {

	@Id
	@Column(name = "file_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "extension", nullable = false, length = 20)
	private String extension;

	@Column(name = "file_url", nullable = false)
	private String fileUrl;

	@Column(name = "file_size", nullable = false)
	private Long fileSize;

	@Column(name = "origin_name", nullable = false, length = 100)
	private String originName;

	@Column(name = "new_name", nullable = false, length = 100)
	private String newName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserEntity userEntity;
}