package com.dnd.gooding.global.s3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class S3Controller {

	@GetMapping("/image")
	public String image() {
		return "image-upload";
	}

	@GetMapping("/video")
	public String video() {
		return "video-upload";
	}

	@GetMapping("/v2/video")
	public String multipartS3() {
		return "multipart-upload-s3";
	}
}
