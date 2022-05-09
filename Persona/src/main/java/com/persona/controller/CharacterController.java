package com.persona.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.persona.dto.CharacterRequest;
import com.persona.dto.CharacterResponse;
import com.persona.service.impl.ICharacterService;

@RestController
@RequestMapping(value = "/character")
public class CharacterController {

	@Autowired
	private ICharacterService service;

	@PostMapping
	private ResponseEntity<CharacterResponse> save(@RequestBody @Valid CharacterRequest dto) {
		Optional<CharacterResponse> response = service.save(dto);

		if (!response.isPresent()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<CharacterResponse>(response.get(), HttpStatus.CREATED);
	}

	@GetMapping
	private Page<CharacterResponse> findAll(Pageable pageable) {
		return service.findAll(pageable);
	}

	@GetMapping(value = "/{id}")
	private ResponseEntity<com.persona.model.Character> findById(Long id) {
		Optional<com.persona.model.Character> response = service.findById(id);

		if (!response.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<com.persona.model.Character>(response.get(), HttpStatus.CREATED);
	}
}
