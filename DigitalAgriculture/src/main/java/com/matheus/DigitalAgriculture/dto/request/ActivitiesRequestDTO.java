package com.matheus.DigitalAgriculture.dto.request;

import com.matheus.DigitalAgriculture.enums.TypeActivities;
import com.matheus.DigitalAgriculture.enums.validation.ValueOfEnum;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ActivitiesRequestDTO(@NotNull @ValueOfEnum(enumClass = TypeActivities.class) String type,
                                   @NotNull LocalDate date, String observations) {
}
