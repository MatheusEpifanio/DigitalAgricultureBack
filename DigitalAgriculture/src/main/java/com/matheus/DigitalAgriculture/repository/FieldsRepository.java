package com.matheus.DigitalAgriculture.repository;

import com.matheus.DigitalAgriculture.model.Fields;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FieldsRepository extends JpaRepository<Fields, Long> {
    Page<Fields> findByUsers_id(long userId, Pageable pageable);
}
