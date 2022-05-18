package com.server.health.client;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.server.health.dto.CharacterResponse;

@FeignClient(name = "characterClient", url = "http://localhost:8080/character")
public interface CharacterClient {
	
	@RequestMapping(method = RequestMethod.GET, value = "/{uniqueServerHash}", consumes = "application/json", produces = "application/json")
	Optional<CharacterResponse> findCharacter(@PathVariable(value="uniqueServerHash") String hash);
	
}

