package com.matheus.DigitalAgriculture.dto.response;

import com.matheus.DigitalAgriculture.model.Activities;

import java.math.BigDecimal;
import java.util.List;

public record FieldDetailsResponseDTO(long id, String name, String crop, int areaHectares, BigDecimal latitude,
                                      BigDecimal longitude, List<ActivitiesResponseDTO> activities) {
}
