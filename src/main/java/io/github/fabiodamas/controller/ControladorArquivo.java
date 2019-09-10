package io.github.fabiodamas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControladorArquivo {
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
}
