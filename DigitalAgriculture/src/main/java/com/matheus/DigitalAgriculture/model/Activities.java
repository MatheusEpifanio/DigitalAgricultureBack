package com.matheus.DigitalAgriculture.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.matheus.DigitalAgriculture.enums.Converters.TypeConverter;
import com.matheus.DigitalAgriculture.enums.TypeActivities;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Activities {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(nullable = true)
    @Convert(converter = TypeConverter.class)
    private TypeActivities type;

    @NotNull
    @Column(nullable = false)
    private LocalDate date;

    @Column
    private String observations;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "field_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Fields fields;
}
