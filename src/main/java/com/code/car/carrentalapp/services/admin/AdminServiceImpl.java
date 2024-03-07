package com.code.car.carrentalapp.services.admin;

import com.code.car.carrentalapp.dto.CarDto;
import com.code.car.carrentalapp.entity.Car;
import com.code.car.carrentalapp.repository.CarRepository;
import com.code.car.carrentalapp.services.images.ImageService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final CarRepository carRepository;
    private final ImageService imageService;

    @Override
    public boolean postCar(CarDto carDto) throws IOException {
        try{
            var newCar = Car.builder()
                    .name(carDto.getName())
                    .brand(carDto.getBrand())
                    .color(carDto.getColor())
                    .price(carDto.getPrice())
                    .year(carDto.getYear())
                    .type(carDto.getType())
                    .description(carDto.getDescription())
                    .transmission(carDto.getTransmission())
                    .imagePath(imageService.saveImageToStorage(carDto.getImagePosted()))
                    .build();
            carRepository.save(newCar);
            return  true;
        } catch (Exception ex){
            return false;
        }
    }

    @Override
    public List<CarDto> getAllCars() {
        return carRepository.findAll().stream().map(Car::getCarDto).collect(Collectors.toList());
    }

    @Override
    public CarDto deleteCar(Long id) {
        // Check if the car exists in the repository
        Optional<Car> carOptional = carRepository.findById(id);
        // If the car does not exist, throw an exception
        Car car = carOptional.orElseThrow(() -> new EntityNotFoundException("Car with ID " + id + " not found"));
        // Delete the car from the repository
        carRepository.delete(car);
        // Return the DTO of the deleted car
        return car.getCarDto();
    }

    @Override
    public CarDto getCarById(Long id) {
        // Check if the car exists in the repository
        Optional<Car> carOptional = carRepository.findById(id);
        // If the car does not exist, throw an exception
        Car car = carOptional.orElseThrow(() -> new EntityNotFoundException("Car with ID " + id + " not found"));

        // Return the DTO of the deleted car
        return car.getCarDto();
    }

    @Override
    public boolean updateCar(Long carId, CarDto carDto) throws IOException {
        Optional<Car> optionalCar = carRepository.findById(carId);
        if(optionalCar.isPresent()){
            Car existingCar = optionalCar.get();
            if(carDto.getImagePosted() != null){
                existingCar.setImagePath(imageService.saveImageToStorage(carDto.getImagePosted()));
                existingCar.setName(carDto.getName());
                existingCar.setBrand(carDto.getBrand());
                existingCar.setColor(carDto.getColor());
                existingCar.setType(carDto.getType());
                existingCar.setTransmission(carDto.getTransmission());
                existingCar.setDescription(carDto.getDescription());
                existingCar.setPrice(carDto.getPrice());
                existingCar.setYear(carDto.getYear());
                carRepository.save(existingCar);
                return true;
            }

        }
        return false;
    }

}
