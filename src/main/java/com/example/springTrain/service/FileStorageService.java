package com.example.springTrain.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

	String uploadPath = System.getProperty("user.dir")+"/src/main/resources/static/uploads/";
	
	//for saving file in local storage
	public String saveFile(MultipartFile file) {
        try {
        if(!file.isEmpty()) {
       	
        	Path uploadDir = Paths.get(uploadPath);
			if(!Files.exists(uploadDir)) {//creating new folder if it doesnot exists
				Files.createDirectories(uploadDir);
			}
			
        	byte[] fileBytes = file.getBytes();
        	String filename = file.getOriginalFilename();
        	
			String uniquePath = System.currentTimeMillis()+"_"+filename;
			
			Path filePath = uploadDir.resolve(uniquePath);

			Files.write(filePath,fileBytes);
			return uniquePath;
			}
        } catch (IOException e) {
				e.printStackTrace();
		}
		return null;
	}
	
	public Resource getFileAsResource(String fileName) {
        try {
            Path filePath = Paths.get(uploadPath).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found: " + fileName);
            }
        } catch (MalformedURLException | FileNotFoundException e) {
            throw new RuntimeException("Error while accessing file: " + fileName, e);
        }
    }

	public Boolean checkImageType(MultipartFile file) {
	    
		String imageContentType = file.getContentType();

		if (imageContentType == null ||
		    		( !imageContentType.equals("image/png") &&
		    		  !imageContentType.equals("image/jpg") &&
		    		  !imageContentType.equals("image/jpeg")&&
		    		  !imageContentType.equals("image/webp") ) 
		    ) {		
			return false;
		}	
		return true;
	}
	public Boolean checkFileType(MultipartFile file) {
		 
	    String filecontentType = file.getContentType();
	    
		 if (filecontentType == null || !filecontentType.equals("application/pdf")) {
				return false;

		 }
		return true;
	}

}
