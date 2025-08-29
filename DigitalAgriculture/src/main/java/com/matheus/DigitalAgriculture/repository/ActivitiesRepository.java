package com.matheus.DigitalAgriculture.repository;

import com.matheus.DigitalAgriculture.model.Activities;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivitiesRepository extends JpaRepository<Activities, Long> {
    Page<Activities> findByFields_id(long fieldId, Pageable pageable);
}
