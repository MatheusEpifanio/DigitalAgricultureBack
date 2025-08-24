package com.matheus.DigitalAgriculture.repository;

import com.matheus.DigitalAgriculture.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsersRepository extends JpaRepository<Users, Long> {
    UserDetails findByEmail(String email);
}
