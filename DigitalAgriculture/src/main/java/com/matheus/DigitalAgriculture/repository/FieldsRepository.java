package com.matheus.DigitalAgriculture.repository;

import com.matheus.DigitalAgriculture.model.Fields;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FieldsRepository extends JpaRepository<Fields, Long> {
    Page<Fields> findByUsers_id(long userId, Pageable pageable);

    @Query("SELECT f FROM Fields f left join fetch f.activities where f.id = :id")
    Fields findFindWithDetailsById(@Param("id") long id);
}
