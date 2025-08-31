package com.matheus.DigitalAgriculture.services;

import com.matheus.DigitalAgriculture.dto.request.FieldsRequestDTO;
import com.matheus.DigitalAgriculture.dto.response.FieldDetailsResponseDTO;
import com.matheus.DigitalAgriculture.dto.response.FieldsResponseDTO;
import com.matheus.DigitalAgriculture.dto.mapper.FieldsMapper;
import com.matheus.DigitalAgriculture.dto.response.PaginationResponseDTO;
import com.matheus.DigitalAgriculture.exception.RegisterNotFound;
import com.matheus.DigitalAgriculture.model.Fields;
import com.matheus.DigitalAgriculture.model.Users;
import com.matheus.DigitalAgriculture.repository.FieldsRepository;
import com.matheus.DigitalAgriculture.repository.UsersRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@Validated
public class FieldsServices {

    @Autowired
    FieldsRepository fieldsRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    FieldsMapper fieldsMapper;

    public PaginationResponseDTO<FieldsResponseDTO> findFieldsByUserId(@RequestParam(defaultValue = "0") @PositiveOrZero int numberPage,
                                                                       @RequestParam(defaultValue = "10") @Positive int lengthPage,
                                                                       @NotNull @Positive long userId) {

        Page<Fields> fieldsPage = fieldsRepository.findByUsersId(userId, PageRequest.of(numberPage, lengthPage));
        List<FieldsResponseDTO> listFields = fieldsPage.get().map(fieldsMapper::toDto).toList();

        return new PaginationResponseDTO<FieldsResponseDTO>(listFields, fieldsPage.getTotalElements(), fieldsPage.getTotalPages());
    }

    public void insertFields(@Valid FieldsRequestDTO fieldsRequestDTO, long userId) {
        Users users = usersRepository.findById(userId)
                .orElseThrow(() -> new RegisterNotFound("Usuário não encontrado"));

        fieldsRepository.save(fieldsMapper.toEntity(fieldsRequestDTO, users));
    }

    public FieldDetailsResponseDTO findFindWithDetailsById(@NotNull @Positive long id) {
        Fields field = fieldsRepository.findFindWithDetailsById(id)
                    .orElseThrow(() -> new RegisterNotFound("Talhão não encontrado!"));
        return fieldsMapper.detailsToDto(field);
    }
}
