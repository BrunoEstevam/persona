package com.persona.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<com.persona.model.Character, Long> {
	  boolean existsByName(String nome);
}
