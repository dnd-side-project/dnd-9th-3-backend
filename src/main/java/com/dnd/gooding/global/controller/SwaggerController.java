package com.dnd.gooding.global.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/docs")
	public String docs() {
		logger.info("[SwaggerController] docs");
		return "redirect:swagger-ui/index.html";
	}
}
