package com.dnd.gooding.global.s3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class S3UploaderController {

	@GetMapping("/image")
	public String image() {
		return "image-upload";
	}
}
