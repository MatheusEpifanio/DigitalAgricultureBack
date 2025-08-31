package com.matheus.DigitalAgriculture.controller;

import com.matheus.DigitalAgriculture.dto.request.LoginRequestDTO;
import com.matheus.DigitalAgriculture.dto.response.LoginResponseDTO;
import com.matheus.DigitalAgriculture.dto.request.UsersRequestDTO;
import com.matheus.DigitalAgriculture.services.AuthenticationUsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@Validated
public class AuthenticationController {

    @Autowired
    AuthenticationUsersService authenticationUsersService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid UsersRequestDTO usersRequestDTO) {
         authenticationUsersService.register(usersRequestDTO);
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        return authenticationUsersService.login(loginRequestDTO);
    }
}
