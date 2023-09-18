package com.dnd.gooding.domain.file.domain;

import com.dnd.gooding.domain.record.domain.Record;
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
	private final Record record;

	@Builder
	public File(Long id, String extension, String fileUrl, Long fileSize, String originName, String newName,
		User user, Record record) {
		this.id = id;
		this.extension = extension;
		this.fileUrl = fileUrl;
		this.fileSize = fileSize;
		this.originName = originName;
		this.newName = newName;
		this.user = user;
		this.record = record;
	}

	public static File create(FileCreate fileCreate, User user) {
		return File.builder()
			.extension(fileCreate.getExtension())
			.fileUrl(fileCreate.getFileUrl())
			.fileSize(fileCreate.getFileSize())
			.originName(fileCreate.getOriginName())
			.newName(fileCreate.getNewName())
			.user(user)
			.build();
	}

	public static File create(FileCreate fileCreate, Record record) {
		return File.builder()
			.extension(fileCreate.getExtension())
			.fileUrl(fileCreate.getFileUrl())
			.fileSize(fileCreate.getFileSize())
			.originName(fileCreate.getOriginName())
			.newName(fileCreate.getNewName())
			.user(record.getUser())
			.record(record)
			.build();
	}

	public String extractExtension(String fileUrl) {
		int position = fileUrl.lastIndexOf(".");
		return fileUrl.substring(position + 1);
	}
}
