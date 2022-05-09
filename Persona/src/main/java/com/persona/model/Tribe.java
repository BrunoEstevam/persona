package com.persona.model;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

public class Tribe {

	private @Getter String id = "tribeId";
	private @Getter int code = 1;
	private @Getter String name = "Warrior";
	private Map<String, String> attributes;

	public Map<String, String> getAttributes() {
		attributes = new HashMap<String, String>();
		attributes.put("Visao noturan", "atributeId1");

		return attributes;
	}
	
}
