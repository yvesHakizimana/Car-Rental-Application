package com.code.car.carrentalapp.entity;

import com.code.car.carrentalapp.dto.CarDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String name;
    private String type;
    private String color;
    private String transmission;
    private String description;
    private Long price;
    private Date year;
    private String imagePath;

    public CarDto getCarDto(){
        return CarDto
                .builder()
                .id(id)
                .name(name)
                .brand(brand)
                .type(type)
                .color(color)
                .transmission(transmission)
                .description(description)
                .price(price)
                .year(year)
                .imagePath(imagePath)
                .build();
    }
}
