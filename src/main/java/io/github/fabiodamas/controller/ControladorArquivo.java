package io.github.fabiodamas.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ControladorArquivo {
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@PostMapping("/")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redireAttributes ) {
		Path rootCaminho;
		String nomeArquivo;
		
		nomeArquivo = StringUtils.cleanPath(file.getOriginalFilename());
		
		try {
			InputStream inputStream = file.getInputStream();
			new File("teste").mkdir();			
			rootCaminho = Paths.get("teste");
			
	        Files.copy(inputStream, rootCaminho.resolve(nomeArquivo),
	                StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}

		redireAttributes.addFlashAttribute("message",
                "Arquivo upado com sucesso!!!" + file.getOriginalFilename() + "!");
		
        return "redirect:/";
	}
	
}
