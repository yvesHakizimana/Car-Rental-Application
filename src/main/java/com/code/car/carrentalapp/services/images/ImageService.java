package com.code.car.carrentalapp.services.images;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    String saveImageToStorage(MultipartFile file) throws IOException;
}
