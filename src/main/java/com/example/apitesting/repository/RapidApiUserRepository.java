package com.example.apitesting.repository;

import com.example.apitesting.domain.RapidApiUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RapidApiUserRepository extends JpaRepository<RapidApiUser,String> {

    Optional<RapidApiUser> findByUsername(String username);

}
