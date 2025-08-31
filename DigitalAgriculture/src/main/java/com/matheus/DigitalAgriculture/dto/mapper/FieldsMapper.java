package com.matheus.DigitalAgriculture.dto.mapper;

import com.matheus.DigitalAgriculture.dto.request.FieldsRequestDTO;
import com.matheus.DigitalAgriculture.dto.response.ActivitiesResponseDTO;
import com.matheus.DigitalAgriculture.dto.response.FieldDetailsResponseDTO;
import com.matheus.DigitalAgriculture.dto.response.FieldsResponseDTO;
import com.matheus.DigitalAgriculture.model.Fields;
import com.matheus.DigitalAgriculture.model.Users;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FieldsMapper {

    public FieldsResponseDTO toDto(Fields field) {
        if(field == null) {
            return null;
        }

        return new FieldsResponseDTO(field.getId(), field.getName());
    }

    public FieldDetailsResponseDTO detailsToDto(Fields field) {
        if(field == null) {
            return null;
        }

        List<ActivitiesResponseDTO> activitiesList = field.getActivities().stream()
                .map(activities -> {
                    return new ActivitiesResponseDTO(activities.getId(), activities.getType().getValue(), activities.getDate(),
                            activities.getObservations(), field.getId());
                })
                .toList();

        return new FieldDetailsResponseDTO(field.getId(), field.getName(), field.getCrop(), field.getAreaHectares(),
                    field.getLatitude(), field.getLongitude(), activitiesList);
    }


    public Fields toEntity(FieldsRequestDTO fieldsRequestDTO, Users users) {
        if(fieldsRequestDTO == null){
            return null;
        }

        Fields fields = new Fields();

        if(fieldsRequestDTO.id() != null){
            fields.setId(fieldsRequestDTO.id());
        }

        fields.setName(fieldsRequestDTO.name());
        fields.setCrop(fieldsRequestDTO.crop());
        fields.setAreaHectares(fieldsRequestDTO.areaHectares());
        fields.setLongitude(fieldsRequestDTO.longitude());
        fields.setLatitude(fieldsRequestDTO.latitude());
        fields.setUsers(users);

        return fields;
    }
}
