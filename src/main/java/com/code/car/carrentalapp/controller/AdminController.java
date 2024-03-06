package com.code.car.carrentalapp.controller;

import com.code.car.carrentalapp.dto.CarDto;
import com.code.car.carrentalapp.services.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    @PostMapping("/car")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> postCar(@ModelAttribute CarDto carDto) throws IOException {

        System.out.println(carDto);

        boolean isPostedSuccessfully = adminService.postCar(carDto);
        if(isPostedSuccessfully){
            return ResponseEntity.status(HttpStatus.CREATED).body("The car object created successfully");
        }else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/cars")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> getAllCars() {
        return ResponseEntity.ok(adminService.getAllCars());
    }



    @DeleteMapping("/car/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteCar(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(adminService.deleteCar(id));
    }

}
