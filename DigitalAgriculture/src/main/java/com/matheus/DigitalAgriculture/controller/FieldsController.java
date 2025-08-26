package com.matheus.DigitalAgriculture.controller;

import com.matheus.DigitalAgriculture.dto.request.FieldsRequestDTO;
import com.matheus.DigitalAgriculture.dto.response.FieldsResponseDTO;
import com.matheus.DigitalAgriculture.dto.response.PaginationResponseDTO;
import com.matheus.DigitalAgriculture.services.FieldsServices;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fields")
@Validated
public class FieldsController {

    @Autowired
    FieldsServices fieldsServices;

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void teste(){

    }

    @GetMapping("/{users_id}")
    public PaginationResponseDTO<FieldsResponseDTO> getFieldsByUserId(@RequestParam(defaultValue = "0") @PositiveOrZero int numberPage,
                                                                      @RequestParam(defaultValue = "10") @Positive int lengthPage,
                                                                      @PathVariable @NotNull @Positive long users_id) {
        return fieldsServices.getFieldsByUserId(numberPage, lengthPage, users_id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insertFields(@RequestBody @Valid FieldsRequestDTO fieldsRequestDTO) {
        fieldsServices.insertFields(fieldsRequestDTO);
    }

}
