package io.github.fabiodamas.Utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class Armazenador {
	Path rootCaminho;
	
    public Path load(String filename) {
        return rootCaminho.resolve(filename);
    }	
	
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new Error("Não consegiu ler o arquivo: " + filename);
            }
        }
        catch (MalformedURLException e) {
            throw new Error("Não consegiu ler o arquivo: " + filename);
            
        }
    }
    
    
	public Stream<Path> loadAll() {
        try {
    	    Path rootLocation;
            rootLocation = Paths.get("src/main/resources/static/arquivos");
            
            return Files.walk(rootLocation, 1)
                .filter(path -> !path.equals(rootLocation))
                .map(rootLocation::relativize);
        }
        catch (IOException e) {
        	 throw new Error("deu pau");
        }

    }
    
	public void envia(MultipartFile file) {
		String nomeArquivo;
		
		nomeArquivo = StringUtils.cleanPath(file.getOriginalFilename());
		
		try {
			InputStream inputStream = file.getInputStream();
			new File("arquivos").mkdir();			
			rootCaminho = Paths.get("src/main/resources/static/arquivos");
			
	        Files.copy(inputStream, rootCaminho.resolve(nomeArquivo),
	                StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}	}
}
