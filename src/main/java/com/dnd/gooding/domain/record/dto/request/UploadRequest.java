package com.dnd.gooding.domain.record.dto.request;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.dnd.gooding.domain.record.model.RecordOpenStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UploadRequest {
	@NotNull
	private String title;
	@NotNull
	private String description;
	@NotNull @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime recordDate;
	@NotNull
	private String placeTitle;
	@NotNull
	private Double placeLatitude;
	@NotNull
	private Double placeLongitude;
	@NotNull
	private RecordOpenStatus recordOpen;
	@NotNull
	private Integer recordScore;
}
