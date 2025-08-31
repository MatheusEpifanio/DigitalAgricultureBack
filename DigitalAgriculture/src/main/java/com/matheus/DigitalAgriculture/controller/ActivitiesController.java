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

import java.util.List;

@RestController
@RequestMapping("api/fields/{field_id}/activities")
public class ActivitiesController {

    @Autowired
    ActivitiesServices activitiesServices;

    @GetMapping
    public List<ActivitiesResponseDTO> getActivities(@PathVariable @NotNull long field_id) {
        return activitiesServices.findActivitiesByFieldId(field_id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insertActivities(@PathVariable @NotNull @Positive long field_id, @RequestBody ActivitiesRequestDTO activitiesRequestDTO) {
        System.out.println(activitiesRequestDTO);
        activitiesServices.insertActivities(field_id, activitiesRequestDTO);
    }
}
