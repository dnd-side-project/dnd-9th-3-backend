package com.dnd.gooding.domain.file.controller.response;

import com.dnd.gooding.domain.file.domain.File;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "기록 파일")
@Getter
@Builder
public class RecordFileResponse {

	private Long id;
	private String fileUrl;

	public static RecordFileResponse from(File file) {
		return RecordFileResponse.builder()
			.id(file.getId())
			.fileUrl(file.getFileUrl())
			.build();
	}
}
