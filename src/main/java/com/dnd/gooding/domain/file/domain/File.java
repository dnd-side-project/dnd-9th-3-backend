package com.dnd.gooding.domain.file.domain;

import com.dnd.gooding.domain.user.domain.User;

import lombok.Builder;
import lombok.Getter;

@Getter
public class File {

	private final Long id;
	private final String extension;
	private final String fileUrl;
	private final Long fileSize;
	private final String originName;
	private final String newName;
	private final User user;

	@Builder
	public File(Long id, String extension, String fileUrl, Long fileSize, String originName, String newName,
		User user) {
		this.id = id;
		this.extension = extension;
		this.fileUrl = fileUrl;
		this.fileSize = fileSize;
		this.originName = originName;
		this.newName = newName;
		this.user = user;
	}
}
