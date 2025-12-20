package com.kanbine.backend.controllers;

import com.kanbine.backend.dto.request.LoginRequest;
import com.kanbine.backend.dto.request.UserRequest;
import com.kanbine.backend.dto.response.JwtResponse;
import com.kanbine.backend.dto.response.UserResponse;
import com.kanbine.backend.services.UserService;
import com.kanbine.backend.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // We need to fetch the user ID. Since UserDetails doesn't have it by default,
        // we can fetch the user by email or cast if we used a custom UserDetails
        // implementation.
        // For now, let's fetch by email to be safe and simple.
        UserResponse user = userService.getAllUsers().stream()
                .filter(u -> u.getEmail().equals(userDetails.getUsername()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Error: User not found."));

        return ResponseEntity.ok(new JwtResponse(jwt,
                user.getId(),
                user.getEmail()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequest signUpRequest) {
        // Check if user exists? UserService.saveUser might handle it or throw error.
        // For now, let's just call saveUser.
        UserResponse userResponse = userService.saveUser(signUpRequest);
        return ResponseEntity.ok(userResponse);
    }
}
