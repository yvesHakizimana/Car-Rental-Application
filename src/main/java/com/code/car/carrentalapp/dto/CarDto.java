package com.code.car.carrentalapp.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@ToString
@Builder
public class CarDto {
    private Long id;
    private String brand;
    private String name;
    private String type;
    private String transmission;
    private String description;
    private String color;
    private Long price;
    @DateTimeFormat(pattern = "yyyy")
    private Date year;
    private MultipartFile imagePosted;
    private String imagePath;
}
