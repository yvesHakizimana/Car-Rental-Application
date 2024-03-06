package com.code.car.carrentalapp.services.admin;

import com.code.car.carrentalapp.dto.CarDto;
import com.code.car.carrentalapp.entity.Car;
import com.code.car.carrentalapp.repository.CarRepository;
import com.code.car.carrentalapp.services.images.ImageService;
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
        Optional<Car> car = carRepository.findById(id);
        if(car.isEmpty())
            throw new IllegalArgumentException("The car with the id : " + id + " is not present");
        else {
            carRepository.deleteById(id);
            return car.get().getCarDto();
        }

    }
}
