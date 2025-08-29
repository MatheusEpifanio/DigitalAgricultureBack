package com.matheus.DigitalAgriculture.enums.Converters;

import com.matheus.DigitalAgriculture.enums.TypeActivities;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;


@Converter(autoApply = true)
public class  TypeConverter implements AttributeConverter<TypeActivities, String> {

    @Override
    public String convertToDatabaseColumn(TypeActivities typeActivities) {
        if (typeActivities == null){
            return null;
        }
        return typeActivities.getValue();
    }


    @Override
    public TypeActivities convertToEntityAttribute(String value) {
        return TypeActivities.getDescription(value);
    }
}
