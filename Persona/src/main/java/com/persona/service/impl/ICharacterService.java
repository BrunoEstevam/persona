package com.persona.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.persona.dto.CharacterRequest;
import com.persona.dto.CharacterResponse;

public interface ICharacterService {

	Optional<CharacterResponse> save(CharacterRequest dto);
	Page<CharacterResponse> findAll(Pageable pageable);
	Optional<com.persona.model.Character> findById(Long id);
}
