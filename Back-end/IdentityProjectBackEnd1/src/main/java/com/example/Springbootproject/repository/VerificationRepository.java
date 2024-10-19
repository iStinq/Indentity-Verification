package com.example.Springbootproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Springbootproject.model.Verification;

@Repository
public interface VerificationRepository extends JpaRepository<Verification, Long> {

}
