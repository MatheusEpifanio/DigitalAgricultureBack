package com.matheus.DigitalAgriculture.dto.response;

import java.time.LocalDate;

public record ActivitiesResponseDTO(long id, String type, LocalDate date,
                                    String observations, long field_id) {
}
