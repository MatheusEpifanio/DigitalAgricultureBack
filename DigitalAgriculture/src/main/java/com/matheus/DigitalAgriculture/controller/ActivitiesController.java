package com.matheus.DigitalAgriculture.controller;

import com.matheus.DigitalAgriculture.dto.request.ActivitiesRequestDTO;
import com.matheus.DigitalAgriculture.dto.response.ActivitiesResponseDTO;
import com.matheus.DigitalAgriculture.dto.response.PaginationResponseDTO;
import com.matheus.DigitalAgriculture.services.ActivitiesServices;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fields/{field_id}/activities")
public class ActivitiesController {

    @Autowired
    ActivitiesServices activitiesServices;

    @GetMapping
    public PaginationResponseDTO<ActivitiesResponseDTO> getActivities(@RequestParam(defaultValue = "0") @PositiveOrZero int numberPage,
                                                                      @RequestParam(defaultValue = "10") @Positive int lengthPage,
                                                                      @PathVariable @NotNull long field_id) {
        return activitiesServices.findActivitiesByFieldId(numberPage, lengthPage, field_id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insertActivities(@PathVariable @NotNull @Positive long field_id, @RequestBody ActivitiesRequestDTO activitiesRequestDTO) {
        activitiesServices.insertActivities(field_id, activitiesRequestDTO);
    }


}
