package com.code.car.carrentalapp.services.auth;

import com.code.car.carrentalapp.dto.SignupRequest;
import com.code.car.carrentalapp.dto.UserDto;
import com.code.car.carrentalapp.entity.User;
import com.code.car.carrentalapp.enums.UserRole;
import com.code.car.carrentalapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;

    @Override
    public UserDto createCustomer(SignupRequest signupRequest) {
        var createdCustomer = User.builder()
                .firstName(signupRequest.getFirstName())
                .lastName(signupRequest.getLastName())
                .email(signupRequest.getEmail())
                .password(signupRequest.getPassword())
                .userRole(UserRole.CUSTOMER)
                .build();
        userRepository.save(createdCustomer);
        var userDto = new UserDto();
        userDto.setId(createdCustomer.getId());
        return userDto;
    }

    @Override
    public boolean hasCustomerWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }
}
