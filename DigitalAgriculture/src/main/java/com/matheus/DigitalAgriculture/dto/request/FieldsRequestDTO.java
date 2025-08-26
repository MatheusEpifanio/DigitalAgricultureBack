package com.matheus.DigitalAgriculture.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record FieldsRequestDTO(Long id, @NotBlank String name, @NotBlank String crop, @NotNull @Positive Integer areaHectares,
                               @NotNull BigDecimal longitude, @NotNull BigDecimal latitude, @NotNull Long users_id ) {
}
