package com.matheus.DigitalAgriculture.dto.mapper;

import com.matheus.DigitalAgriculture.dto.request.FieldsRequestDTO;
import com.matheus.DigitalAgriculture.dto.response.FieldDetailsResponseDTO;
import com.matheus.DigitalAgriculture.dto.response.FieldsResponseDTO;
import com.matheus.DigitalAgriculture.model.Fields;
import com.matheus.DigitalAgriculture.model.Users;
import org.springframework.stereotype.Component;

@Component
public class FieldsMapper {

    public FieldsResponseDTO toDto(Fields field) {
        if(field == null) {
            return null;
        }

        return new FieldsResponseDTO(field.getId(), field.getName());
    }

    public FieldDetailsResponseDTO toDetailsDto(Fields field) {
        if(field == null) {
            return null;
        }

        return new FieldDetailsResponseDTO(field.getId(), field.getName(), field.getCrop(), field.getAreaHectares(),
                    field.getLatitude(), field.getLongitude(), field.getActivities());
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
