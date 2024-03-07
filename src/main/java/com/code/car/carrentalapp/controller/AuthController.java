package com.code.car.carrentalapp.controller;
import com.code.car.carrentalapp.dto.AuthenticationRequest;
import com.code.car.carrentalapp.dto.AuthenticationResponse;
import com.code.car.carrentalapp.dto.SignupRequest;
import com.code.car.carrentalapp.dto.UserDto;
import com.code.car.carrentalapp.services.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

     private final AuthService authService;

     @PostMapping("/signup")
     public ResponseEntity<?> signUpCustomer(@RequestBody SignupRequest userCredentials){
         if(authService.hasCustomerWithEmail(userCredentials.getEmail()))
             return new ResponseEntity<>("Customer already exists with this email", HttpStatus.NOT_ACCEPTABLE);
         UserDto createdCustomerDto = authService.createCustomer(userCredentials);
         if(createdCustomerDto == null)
             return new ResponseEntity<>("Customer not created", HttpStatus.BAD_REQUEST);
         return new ResponseEntity<>(createdCustomerDto, HttpStatus.CREATED);
     }

     @PostMapping("/login")
     public ResponseEntity<?> signInCustomer(@RequestBody AuthenticationRequest authRequest) throws BadCredentialsException, DisabledException, UsernameNotFoundException {
         try{
             AuthenticationResponse authResponse = authService.authenticate(authRequest);
             return ResponseEntity.ok(authResponse);
         } catch (RuntimeException ex){
             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
         }
     }
}


