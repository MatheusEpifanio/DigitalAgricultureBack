package com.matheus.DigitalAgriculture.enums;

import com.matheus.DigitalAgriculture.exception.RegisterNotFound;

import java.util.stream.Stream;

public enum TypeActivities {
    PLANTING("Plantio"),  FERTILIZING("Adubação"), HARVEST("Colheita");

    private final String value;

    TypeActivities(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static TypeActivities getDescription(String type){
        return Stream.of(TypeActivities.values())
                .filter( typeActivities -> typeActivities.toString().equals(type))
                .findFirst()
                .orElseThrow(() -> new RegisterNotFound("Ativadade não encontrada!"));
    }
}
