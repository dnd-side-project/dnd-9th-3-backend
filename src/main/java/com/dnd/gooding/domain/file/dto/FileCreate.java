package com.dnd.gooding.domain.file.dto;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FileCreate {

	private String fileDc;
	private Long fileSequence;
	private String extension;
	private String fileUrl;
	private String originName;
	private String newName;
	private String fileDir;
	private Long fileSize;

	public FileCreate(String environment, String basicDir) {
		if ("local".equals(environment)) {
			this.fileDir = System.getProperty("user.dir") + basicDir;
		} else if ("development".equals(environment)) {
			this.fileDir = basicDir;
		}
	}

	@Builder
	public FileCreate(String extension, String fileUrl, String originName, String newName, Long fileSize) {
		this.extension = extension;
		this.fileUrl = fileUrl;
		this.originName = originName;
		this.newName = newName;
		this.fileSize = fileSize;
	}

	public Optional<File> convert(MultipartFile multipartFile) throws IOException {
		if (multipartFile.isEmpty()) {
			return Optional.empty();
		}

		String originalFilename = multipartFile.getOriginalFilename();

		String newFileName = createStoreFileName(originalFilename);

		//파일 업로드
		File file = new File(fileDir + newFileName);
		multipartFile.transferTo(file);

		this.originName = originalFilename;
		this.newName = newFileName;
		this.fileSize = file.length();

		return Optional.of(file);
	}

	public FileCreate create(String fileUrl) {
		return FileCreate.builder()
			.extension(extension)
			.fileUrl(fileUrl)
			.originName(originName)
			.newName(newName)
			.fileSize(fileSize)
			.build();
	}

	private String createStoreFileName(String originalFilename) {
		String extension = extractExtension(originalFilename);
		String uuid = UUID.randomUUID().toString();
		this.extension = extension;
		return uuid + "." + extension;
	}

	private String extractExtension(String originName) {
		int position = originName.lastIndexOf(".");
		return originName.substring(position + 1);
	}
}
