package com.server.health.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.server.health.dto.CharacterResponse;
import com.server.health.model.ServerHistory;

@Configuration
public class AppConfig {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		
		mapper.createTypeMap(CharacterResponse.class, ServerHistory.class)
				.<String>addMapping(src -> src.getName(), (dest, value) -> dest.setCharacterName(value))
				.<String>addMapping(src -> src.getId(), (dest, value) -> dest.setChacterId(value));

		return mapper;
	}
}
