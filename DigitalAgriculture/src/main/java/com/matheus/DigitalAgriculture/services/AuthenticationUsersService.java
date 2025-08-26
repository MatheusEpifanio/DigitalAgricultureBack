package com.matheus.DigitalAgriculture.services;

import com.matheus.DigitalAgriculture.dto.request.LoginRequestDTO;
import com.matheus.DigitalAgriculture.dto.request.UsersRequestDTO;
import com.matheus.DigitalAgriculture.dto.mapper.UsersMapper;
import com.matheus.DigitalAgriculture.dto.response.LoginResponseDTO;
import com.matheus.DigitalAgriculture.exception.AlreadyRegisteredException;
import com.matheus.DigitalAgriculture.exception.RegisterNotFound;
import com.matheus.DigitalAgriculture.model.Users;
import com.matheus.DigitalAgriculture.repository.UsersRepository;
import com.matheus.DigitalAgriculture.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class AuthenticationUsersService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    TokenService tokenService;

    @Autowired
    UsersMapper usersMapper;

    @Autowired
    AuthenticationManager authenticationManager;

    public void register(@Valid UsersRequestDTO usersRequestDTO) {
        if(usersRepository.findByEmail(usersRequestDTO.email()) != null){
            throw new AlreadyRegisteredException("Email já cadastrado!");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(usersRequestDTO.password());

        Users user = usersMapper.toEntity(usersRequestDTO);
        user.setPassword(encryptedPassword);

        usersRepository.save(user);
    }

    public LoginResponseDTO login(@Valid LoginRequestDTO loginRequestDTO) {
        try {
            var emailPassword = new UsernamePasswordAuthenticationToken(loginRequestDTO.email(), loginRequestDTO.password());

            var authentication  = authenticationManager.authenticate(emailPassword);

            String token = tokenService.generateToken((Users) authentication.getPrincipal());

            return new LoginResponseDTO(token, ((Users) authentication.getPrincipal()).getId());
        } catch (Exception exception) {
            throw new RegisterNotFound("Login inválido!");
        }
    }
}
