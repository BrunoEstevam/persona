package com.server.health.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerHistory {
	
	@Id
	private String id;
	private String characterName;
	private String chacterId;
	private LocalDateTime updatedAt;
	private String uniqueServerHash;
}
