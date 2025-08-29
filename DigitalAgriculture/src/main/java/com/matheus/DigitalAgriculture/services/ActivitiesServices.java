package com.matheus.DigitalAgriculture.services;

import com.matheus.DigitalAgriculture.dto.mapper.ActivitiesMapper;
import com.matheus.DigitalAgriculture.dto.request.ActivitiesRequestDTO;
import com.matheus.DigitalAgriculture.dto.response.ActivitiesResponseDTO;
import com.matheus.DigitalAgriculture.dto.response.PaginationResponseDTO;
import com.matheus.DigitalAgriculture.exception.RegisterNotFound;
import com.matheus.DigitalAgriculture.model.Activities;
import com.matheus.DigitalAgriculture.repository.ActivitiesRepository;
import com.matheus.DigitalAgriculture.repository.FieldsRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Valid
public class ActivitiesServices {

    @Autowired
    ActivitiesRepository activitiesRepository;

    @Autowired
    FieldsRepository fieldsRepository;

    @Autowired
    ActivitiesMapper activitiesMapper;

    public PaginationResponseDTO<ActivitiesResponseDTO> findActivitiesByFieldId(@PositiveOrZero int numberPage,
                                                                                @Positive int lengthPage,
                                                                                @NotNull long fieldId){


        Page<Activities> activitiesPage = activitiesRepository.findByFields_id(fieldId, PageRequest.of(numberPage, lengthPage));
        List<ActivitiesResponseDTO> listActivities = activitiesPage.get().map(activitiesMapper::toDto).toList();

        return new PaginationResponseDTO<ActivitiesResponseDTO>(listActivities, activitiesPage.getTotalElements(), activitiesPage.getTotalPages());
    }

    public void insertActivities(@NotNull long fieldId, @Valid ActivitiesRequestDTO activitiesRequestDTO){
        var fields = fieldsRepository.findById(fieldId)
                    .orElseThrow(() -> new RegisterNotFound("Talhão não encontrado"));

        activitiesRepository.save(activitiesMapper.toEntity(activitiesRequestDTO, fields));
    }
}
