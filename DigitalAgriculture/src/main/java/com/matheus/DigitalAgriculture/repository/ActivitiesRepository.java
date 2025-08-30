package com.matheus.DigitalAgriculture.repository;

import com.matheus.DigitalAgriculture.model.Activities;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivitiesRepository extends JpaRepository<Activities, Long> {
    Page<Activities> findByFieldsId(long fieldId, Pageable pageable);
}
