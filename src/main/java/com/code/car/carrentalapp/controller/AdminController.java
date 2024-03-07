package com.code.car.carrentalapp.controller;

import com.code.car.carrentalapp.dto.CarDto;
import com.code.car.carrentalapp.services.admin.AdminService;
import jakarta.persistence.EntityNotFoundException;
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

    @GetMapping("/car/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> getCarById(@PathVariable Long id){
        try {
            CarDto carRetrieved = adminService.getCarById(id);
            return ResponseEntity.ok(carRetrieved);
        } catch (EntityNotFoundException e) {
            // Return 404 response if the car with the given ID is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car with ID " + id + " not found");
        } catch (Exception e) {
            // Handle other unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/car/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteCar(@PathVariable Long id){
        try {
            CarDto deletedCar = adminService.deleteCar(id);
            return ResponseEntity.ok(deletedCar);
        } catch (EntityNotFoundException e) {
            // Return 404 response if the car with the given ID is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car with ID " + id + " not found");
        } catch (Exception e) {
            // Handle other unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/car/{carId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateCar(@PathVariable Long carId, @ModelAttribute CarDto carDto) throws IOException{
        try{
            boolean isUpdated = adminService.updateCar(carId, carDto);
            if(isUpdated)
                return ResponseEntity.status(HttpStatus.OK).build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }
}
