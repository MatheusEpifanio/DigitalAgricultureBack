package com.matheus.DigitalAgriculture.dto.mapper;

import com.matheus.DigitalAgriculture.dto.request.UsersRequestDTO;
import com.matheus.DigitalAgriculture.model.Users;
import org.springframework.stereotype.Component;

@Component
public class UsersMapper {

    public Users toEntity(UsersRequestDTO usersRequestDTO) {
        if(usersRequestDTO == null){
            return null;
        }
        Users user = new Users();
        user.setName(usersRequestDTO.name());
        user.setEmail(usersRequestDTO.email());
        return user;
    }
}
