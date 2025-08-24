package com.matheus.DigitalAgriculture.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record LoginRequestDTO(@NotBlank @Length(max = 50) String email, @NotBlank String password) {
}
