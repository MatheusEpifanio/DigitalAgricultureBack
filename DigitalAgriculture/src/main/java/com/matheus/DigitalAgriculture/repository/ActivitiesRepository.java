package com.matheus.DigitalAgriculture.repository;

import com.matheus.DigitalAgriculture.dto.response.ActivitiesResponseDTO;
import com.matheus.DigitalAgriculture.model.Activities;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivitiesRepository extends JpaRepository<Activities, Long> {
    List<Activities> findByFieldsId(long fieldId);
}
