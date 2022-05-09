package com.persona.controller;

import static br.com.six2six.fixturefactory.Fixture.from;
import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.persona.dto.CharacterRequest;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = { "spring.config.location=classpath:application-test.yml" })
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = { "classpath:populate-database.sql" })
class CharacterControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@BeforeAll
	private static void setup() {
		loadTemplates("com.region.sale.fixture");
	}

	@Test
	@Transactional
	public void shouldCreateSale() throws Exception {
		CharacterRequest dto = from(CharacterRequest.class).gimme("characterRequest");

		assertNotNull(dto);
		
		String dtojson = mapper.writeValueAsString(dto);

		mockMvc.perform(post("/character")
				.header(AUTHORIZATION, "Bearer foo")
				.contentType(APPLICATION_JSON).content(dtojson))
					.andExpect(status().isCreated());
	}

	public void shouldSearchAllSale() {

	}
}
