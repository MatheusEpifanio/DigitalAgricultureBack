package com.matheus.DigitalAgriculture.controller;

import com.matheus.DigitalAgriculture.dto.request.FieldsRequestDTO;
import com.matheus.DigitalAgriculture.dto.response.FieldDetailsResponseDTO;
import com.matheus.DigitalAgriculture.dto.response.FieldsResponseDTO;
import com.matheus.DigitalAgriculture.dto.response.PaginationResponseDTO;
import com.matheus.DigitalAgriculture.model.Users;
import com.matheus.DigitalAgriculture.services.FieldsServices;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fields")
@Validated
public class FieldsController {

    @Autowired
    FieldsServices fieldsServices;

    @GetMapping
    public PaginationResponseDTO<FieldsResponseDTO> findFieldsByUserId(@RequestParam(defaultValue = "0") @PositiveOrZero int numberPage,
                                                                       @RequestParam(defaultValue = "10") @Positive int lengthPage,
                                                                       @AuthenticationPrincipal Users users) {
        return fieldsServices.findFieldsByUserId(numberPage, lengthPage, users.getId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insertFields(@RequestBody @Valid FieldsRequestDTO fieldsRequestDTO) {
        fieldsServices.insertFields(fieldsRequestDTO);
    }

    @GetMapping("/{id}")
    public FieldDetailsResponseDTO findFindWithDetailsById(@PathVariable @NotNull @Positive long id) {
        return fieldsServices.findFindWithDetailsById(id);
    }
}
