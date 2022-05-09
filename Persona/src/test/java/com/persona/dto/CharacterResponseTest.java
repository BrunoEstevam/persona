package com.persona.dto;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import com.google.code.beanmatchers.BeanMatchers;

class CharacterResponseTest {

	@Test
	public void characterResponseTest() {
		MatcherAssert.assertThat(CharacterResponse.class,
				Matchers.allOf(BeanMatchers.hasValidGettersAndSetters(), BeanMatchers.hasValidBeanConstructor()));
	}
}
