package com.dnd.gooding.global.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerController {

	@GetMapping("/api/v1/doc")
	public String redirectSwagger() {
		return "redirect:/swagger-ui/index.html";
	}
}
