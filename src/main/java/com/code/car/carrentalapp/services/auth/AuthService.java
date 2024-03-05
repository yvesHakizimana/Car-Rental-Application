package com.code.car.carrentalapp.services.auth;

import com.code.car.carrentalapp.dto.SignupRequest;
import com.code.car.carrentalapp.dto.UserDto;

public interface AuthService  {
    UserDto createCustomer(SignupRequest signupRequest);
    boolean hasCustomerWithEmail(String email);
}
