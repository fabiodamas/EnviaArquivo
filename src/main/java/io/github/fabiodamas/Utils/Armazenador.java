package io.github.fabiodamas.Utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class Armazenador {
    
	public Stream<Path> loadAll() {
        try {
    	    Path rootLocation;
            rootLocation = Paths.get("arquivos");
            
            return Files.walk(rootLocation, 1)
                .filter(path -> !path.equals(rootLocation))
                .map(rootLocation::relativize);
        }
        catch (IOException e) {
        	 throw new Error("deu pau");
        }

    }
    
	public void envia(MultipartFile file) {
		Path rootCaminho;
		String nomeArquivo;
		
		nomeArquivo = StringUtils.cleanPath(file.getOriginalFilename());
		
		try {
			InputStream inputStream = file.getInputStream();
			new File("arquivos").mkdir();			
			rootCaminho = Paths.get("arquivos");
			
	        Files.copy(inputStream, rootCaminho.resolve(nomeArquivo),
	                StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}	}
}
