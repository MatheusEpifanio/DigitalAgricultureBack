package com.matheus.DigitalAgriculture.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UsersRequestDTO(@NotBlank String email, @NotBlank @Length(max = 50) String name,
                              @Length(max = 50) @NotBlank  String password) {
}
