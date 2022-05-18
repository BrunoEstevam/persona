package com.server.health.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CharacterResponse {

	private Long id;

	private String name;

	private boolean active;

	private String uniqueServerHash;
}
