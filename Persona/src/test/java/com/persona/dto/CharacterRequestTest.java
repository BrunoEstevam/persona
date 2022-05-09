package com.persona.dto;


import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import com.google.code.beanmatchers.BeanMatchers;

class CharacterRequestTest {

	@Test
	public void characterRequestTest() {
		MatcherAssert.assertThat(CharacterRequest.class,
				Matchers.allOf(BeanMatchers.hasValidGettersAndSetters(), BeanMatchers.hasValidBeanConstructor()));
	}

}
