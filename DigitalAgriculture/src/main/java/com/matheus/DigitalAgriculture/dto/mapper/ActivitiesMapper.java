package com.matheus.DigitalAgriculture.dto.mapper;

import com.matheus.DigitalAgriculture.dto.request.ActivitiesRequestDTO;
import com.matheus.DigitalAgriculture.dto.response.ActivitiesResponseDTO;
import com.matheus.DigitalAgriculture.enums.TypeActivities;
import com.matheus.DigitalAgriculture.model.Activities;
import com.matheus.DigitalAgriculture.model.Fields;
import org.springframework.stereotype.Component;

@Component
public class ActivitiesMapper {

    public ActivitiesResponseDTO toDto(Activities activities) {
        if(activities == null){
            return null;
        }

        return new ActivitiesResponseDTO(activities.getId(), activities.getType().getValue(), activities.getDate(),
                   activities.getObservations(), activities.getFields().getId());
    }


    public Activities toEntity(ActivitiesRequestDTO activitiesRequestDTO, Fields fields) {
        if(activitiesRequestDTO == null){
            return null;
        }

        Activities activities = new Activities();

        activities.setType(TypeActivities.getDescription(activitiesRequestDTO.type()));
        activities.setObservations(activitiesRequestDTO.observations());
        activities.setDate(activitiesRequestDTO.date());
        activities.setFields(fields);

        return activities;
    }

}
