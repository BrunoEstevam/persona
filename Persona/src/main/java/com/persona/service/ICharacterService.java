package com.persona.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.persona.dto.CharacterRequest;
import com.persona.dto.CharacterResponse;
import com.persona.model.Character;

public interface ICharacterService {

	Optional<CharacterResponse> save(CharacterRequest dto);
	Optional<CharacterResponse> active(Long id);
	void inactive(String hash);
	
	Page<CharacterResponse> findAll(Pageable pageable);
	Optional<com.persona.model.Character> findById(Long id);
	Optional<Character> findByUniqueServerHash(String uniqueServerHash);
}
