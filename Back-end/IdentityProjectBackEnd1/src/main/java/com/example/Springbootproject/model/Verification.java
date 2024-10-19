package com.example.Springbootproject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "verifications")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Verification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="verification_id")
	private Long id;
	private String firstName;
	private String lastName;
	private String cin;
	private String birthPlace;
	private String birthDate;
	private String photo;
	private String file;
	
	@OneToOne
	private Register user;

}
