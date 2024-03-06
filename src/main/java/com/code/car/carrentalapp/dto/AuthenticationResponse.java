package com.code.car.carrentalapp.dto;

import com.code.car.carrentalapp.enums.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {
    private String jwtToken;
    private UserRole userRole;
    private Long userId;
}
