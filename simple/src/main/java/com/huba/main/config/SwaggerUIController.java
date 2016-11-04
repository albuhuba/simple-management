package com.huba.main.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SwaggerUIController {
	@RequestMapping("/docs")
	public String home() {
		return "redirect:swagger-ui.html";
	}
}
