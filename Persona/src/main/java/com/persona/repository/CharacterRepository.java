package com.persona.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.persona.model.Character;

public interface CharacterRepository extends JpaRepository<com.persona.model.Character, Long> {
	boolean existsByName(String nome);

	Optional<Character> findByUniqueServerHash(String uniqueServerHash);
}
