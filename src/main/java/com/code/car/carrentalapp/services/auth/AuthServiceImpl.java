package com.code.car.carrentalapp.services.auth;

import com.code.car.carrentalapp.dto.AuthenticationRequest;
import com.code.car.carrentalapp.dto.AuthenticationResponse;
import com.code.car.carrentalapp.dto.SignupRequest;
import com.code.car.carrentalapp.dto.UserDto;
import com.code.car.carrentalapp.entity.User;
import com.code.car.carrentalapp.enums.UserRole;
import com.code.car.carrentalapp.repository.UserRepository;
import com.code.car.carrentalapp.utils.JwtUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @Override
    public UserDto createCustomer(SignupRequest signupRequest) {
        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());
        User createdCustomer = User.builder()
                .firstName(signupRequest.getFirstName())
                .lastName(signupRequest.getLastName())
                .email(signupRequest.getEmail())
                .password(encodedPassword)
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

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
            );
            var user = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
            String userJwtToken = jwtUtil.generateToken(user);
            Optional<User> optionalUser = userRepository.findFirstByEmail(user.getUsername());
            if (optionalUser.isPresent()) {
                User userEntity = optionalUser.get();
                return AuthenticationResponse.builder()
                        .jwtToken(userJwtToken)
                        .userId(userEntity.getId())
                        .userRole(userEntity.getUserRole())
                        .build();
            } else {
                // Handle the case when user is not found in database
                throw new RuntimeException("User not found");
            }
        } catch (Exception e) {
            // Handle authentication failure
            throw new RuntimeException("Authentication failed: " + e.getMessage());
        }
    }

    @PostConstruct
    public void createAdminAccount() {
        Optional<User> adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
        if(adminAccount.isEmpty()){
            var newAdminAccount = User.builder()
                    .userRole(UserRole.ADMIN)
                    .firstName("admin")
                    .lastName("account")
                    .email("admin@gmail.com")
                    .password(passwordEncoder.encode("admin"))
                    .build();
            userRepository.save(newAdminAccount);
            System.out.println("Admin account has been created");
        }
    }
}
