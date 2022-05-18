package com.persona.service.impl;

import static com.persona.config.Constantes.CHARACTER_INACTIVE_QUEUE_NAME;
import static com.persona.config.Constantes.CHARACTER_UNIQUESERVERHASH_QUEUE_NAME;

import java.util.Calendar;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.persona.dto.CharacterRequest;
import com.persona.dto.CharacterResponse;
import com.persona.model.Character;
import com.persona.model.Tribe;
import com.persona.repository.CharacterRepository;
import com.persona.service.ICharacterService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CharacterService implements ICharacterService {

	@Autowired
	private CharacterRepository repository;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Override
	public Optional<CharacterResponse> save(CharacterRequest dto) {
		Tribe tribe = new Tribe();
		com.persona.model.Character entity = modelMapper.map(dto, com.persona.model.Character.class);

		verifyNameIsValid(entity.getName());
		entity.setTribeId(tribe.getId());

		Calendar cal = Calendar.getInstance();
		entity.setUniqueServerHash(entity.getName() + cal.get(Calendar.YEAR) + cal.get(Calendar.MONTH)
				+ cal.get(Calendar.DAY_OF_MONTH) + cal.get(Calendar.HOUR) + cal.get(Calendar.MINUTE)
				+ cal.get(Calendar.SECOND) + cal.get(Calendar.MILLISECOND) + tribe.getName().substring(0, 3));

		CharacterResponse response  = modelMapper.map(repository.save(entity), CharacterResponse.class);
		response.setTribe(tribe);
		
		return Optional.of(response);
	}
	
	@Override
	public Optional<CharacterResponse> active(Long id) {
		Optional<Character> charOptional = repository.findById(id);
		
		if (charOptional.isPresent()) {
			Character entity = charOptional.get();
			entity.setActive(true);
			
			rabbitTemplate.convertAndSend(CHARACTER_UNIQUESERVERHASH_QUEUE_NAME, entity.getUniqueServerHash());
			
			return Optional.of(modelMapper.map(repository.save(entity), CharacterResponse.class));
		}
		
		return Optional.of(null);
	}
	
	@Override
	@RabbitListener(queues = CHARACTER_INACTIVE_QUEUE_NAME)
	public void inactive(String hash) {
		Optional<Character> charOptional = repository.findByUniqueServerHash(hash);
		
		if (charOptional.isPresent()) {
			Character entity = charOptional.get();
			entity.setActive(false);
			
			repository.save(entity);
			return;
		} 
		
		log.info("Character com hash {} não foi encontrado");
	}
	
	private void verifyNameIsValid(String name) {
		Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(name);

		if (name.contains(" ")) {
			throw new IllegalArgumentException("Nome do character contem espaços");
		} else if (m.find()) {
			throw new IllegalArgumentException("Nome do character contem caractesres especiais");
		} else if (repository.existsByName(name)) {
			throw new IllegalArgumentException("Nome do character já existente");
		}
	}
	
	// Consultas
	@Override
	public Page<CharacterResponse> findAll(Pageable pageable) {
		return repository.findAll(pageable).map(k -> modelMapper.map(k, CharacterResponse.class));
	}

	@Override
	public Optional<com.persona.model.Character> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public Optional<Character> findByUniqueServerHash(String uniqueServerHash) {
		return repository.findByUniqueServerHash(uniqueServerHash);
	}
}
