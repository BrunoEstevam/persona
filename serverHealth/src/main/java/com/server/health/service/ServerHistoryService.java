package com.server.health.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.health.client.CharacterClient;
import com.server.health.dto.CharacterResponse;
import com.server.health.model.ServerHistory;
import com.server.health.repository.ServerHistoryRepository;
import com.server.health.service.impl.IServerHistoryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ServerHistoryService implements IServerHistoryService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ServerHistoryRepository repository;

	@Autowired
	private CharacterClient characterClient;

	@Override
	public void save(String uniqueServerHash) {
		Optional<CharacterResponse> response = characterClient.findCharacter(uniqueServerHash);

		if (response.isPresent()) {
			ServerHistory history = modelMapper.map(response.get(), ServerHistory.class);
			history.setId(null);
			history.setUpdatedAt(LocalDateTime.now());

			repository.save(history);
		} else {
			log.info("Consulta de charater de hash {} não encontrado", uniqueServerHash);
		}
	}
}
