package org.example.parcial.controllers;

import jakarta.validation.Valid;
import org.example.parcial.domain.dtos.GeneralResponse;
import org.example.parcial.domain.dtos.RegisterDTO;
import org.example.parcial.domain.entities.User;
import org.example.parcial.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<GeneralResponse> registerUser(@RequestBody @Valid RegisterDTO info) {

        User user = userService.findUserByIdentifier(info.getEmail());

        if (user != null) {
            return GeneralResponse.getResponse(HttpStatus.CONFLICT, "User already exist");
        }

        Date birthDate = userService.validDate(info.getFecha_nac());

        if (birthDate == null) {
            return GeneralResponse.getResponse(HttpStatus.BAD_REQUEST, "Invalid date format that must be yyyy-MM-dd");
        }

        userService.registerUser(info, birthDate);

        return GeneralResponse.getResponse(HttpStatus.OK, "Register successful");
    }
}
