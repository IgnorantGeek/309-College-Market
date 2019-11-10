package org.campusmarket.app.controllers;


//import javax.xml.ws.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.campusmarket.app.models.File;
import org.campusmarket.app.models.FileService;
import org.campusmarket.app.models.fileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * a class to represent the controller for files
 * @author fadelalshammasi
 *
 */
@RestController
public class FileController {

	
	@Autowired
	private FileService files;
	
	Log log = LogFactory.getLog(FileController.class);
	
	/**
	 * A method to post a file to the db
	 * @param file
	 * @return a response with the filename, url for downloading the file, type of file,and size of file in byte 
	 */
	 @PostMapping("/upload")
	 public fileResponse uploadFile(@RequestParam("fname") MultipartFile file) {
		File f=files.storeFile(file);
		 String downloadUrl= ServletUriComponentsBuilder.fromCurrentContextPath().path("/download/").path(f.getId()+"").toUriString(); 
		 fileResponse fr=new fileResponse(f.getFileName(),downloadUrl, file.getContentType(),file.getSize()); //size is in byte 
		 return fr;
		 
	 }
	 
	 /**
	  * A method to display(download if using the browser) the content of the file
	  * @param id
	  * @return
	  */
	   @GetMapping("/download/{id}")
	    public ResponseEntity<Resource> downloadFile(@PathVariable int id) {
	       
	        File f = files.getFile(id);

	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(f.getFileType()))
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + f.getFileName() + "\"")
	                .body(new ByteArrayResource(f.getFileData()));
	    }

	}	 
	 
	 

