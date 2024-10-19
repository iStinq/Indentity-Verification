package com.example.Springbootproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Springbootproject.model.ConfirmationToken;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long>  {

	ConfirmationToken findByConfirmationToken(String confirmationToken);
}
