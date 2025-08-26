package com.matheus.DigitalAgriculture.dto.mapper;

import com.matheus.DigitalAgriculture.dto.request.FieldsRequestDTO;
import com.matheus.DigitalAgriculture.dto.response.FieldsResponseDTO;
import com.matheus.DigitalAgriculture.model.Fields;
import com.matheus.DigitalAgriculture.model.Users;
import org.springframework.stereotype.Component;

@Component
public class FieldsMapper {

    public FieldsResponseDTO toDto(Fields fields) {
        if(fields == null){
            return null;
        }

        return new FieldsResponseDTO(fields.getId(), fields.getName());
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
