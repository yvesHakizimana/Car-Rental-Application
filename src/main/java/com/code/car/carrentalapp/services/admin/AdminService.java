package com.code.car.carrentalapp.services.admin;

import com.code.car.carrentalapp.dto.CarDto;
import com.code.car.carrentalapp.entity.Car;

import java.io.IOException;
import java.util.List;

public interface AdminService {
    boolean postCar(CarDto carDto) throws IOException;
    List<CarDto> getAllCars();
    CarDto deleteCar(Long id);
    CarDto getCarById(Long id);
    boolean updateCar(Long carId, CarDto carDto) throws IOException;
}
