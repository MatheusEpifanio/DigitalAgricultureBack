package com.matheus.DigitalAgriculture.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Fields {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String name;

    @Column(nullable = false)
    @NotBlank
    private String crop;

    @Column(nullable = false)
    @NotNull
    private Integer areaHectares;

    @Column(precision = 9, scale = 6, nullable = false)
    @NotNull
    private BigDecimal longitude;

    @Column(precision = 8, scale = 6, nullable = false)
    @NotNull
    private BigDecimal latitude;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Users users;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "fields")
    private List<Activities> activities = new ArrayList<>();
}
