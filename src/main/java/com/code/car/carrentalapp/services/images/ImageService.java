package com.code.car.carrentalapp.services.images;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ImageService {
    private static final String uploadDirectory = "src/main/resources/static/images";
    //Saving the image in a local directory
    public String saveImageToStorage(MultipartFile imageFile) throws IOException {
        int lastIndex = imageFile.getOriginalFilename().lastIndexOf(".");
        String fileExtension = imageFile.getOriginalFilename().substring(lastIndex);
        String uniqueFileName = UUID.randomUUID() + fileExtension;
        Path uploadPath = Path.of(uploadDirectory);
        Path filePath = uploadPath.resolve(uniqueFileName);

        if(!Files.exists(uploadPath))
            Files.createDirectories(uploadPath);

        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }




}
