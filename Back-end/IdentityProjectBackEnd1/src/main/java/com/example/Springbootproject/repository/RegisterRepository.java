package com.example.Springbootproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.Springbootproject.model.Register;

@Repository
public interface RegisterRepository extends JpaRepository<Register, Long>{

	Register findByEmail(String email);
	
}
