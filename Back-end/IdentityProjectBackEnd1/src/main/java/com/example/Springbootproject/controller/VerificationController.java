package com.example.Springbootproject.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.Springbootproject.model.Verification;
import com.example.Springbootproject.repository.VerificationRepository;




@RestController
@RequestMapping
public class VerificationController {

	@Autowired
	private VerificationRepository verificationRepo;
	
	@PostMapping(path = "/verifications", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Verification saveVerification(@RequestParam MultipartFile file, @RequestParam MultipartFile photo,@RequestParam String firstName,@RequestParam String lastName,@RequestParam String cin,@RequestParam String birthPlace,@RequestParam String birthDate) 
			throws IOException {
		Path folderPath= Paths.get(System.getProperty("user.home"),"project-data","userFile" );
		if (!Files.exists(folderPath)) {
			Files.createDirectories(folderPath);
		}
		String filename = UUID.randomUUID().toString();
		Path filePath= Paths.get(System.getProperty("user.home"),"project-data","userFile",filename +".pdf");
		Files.copy(file.getInputStream(), filePath );
		
		String photoFilename = UUID.randomUUID().toString();
		Path photoPath= Paths.get(System.getProperty("user.home"),"project-data","userFile",photoFilename +".jpg");
		Files.copy(photo.getInputStream(), photoPath );
		
		Verification verification = Verification.builder()
				.firstName(firstName)
				.lastName(lastName)
				.cin(cin)
				.birthPlace(birthPlace)
				.birthDate(birthDate)
				.photo(photoPath.toUri().toString())
				.file(filePath.toUri().toString())
				.build();
		
		return verificationRepo.save(verification);
		
	}
}
