package com.persona.model;

import org.junit.jupiter.api.Test;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;

class CharacterTest {

	@Test
	void characterModelTeste() {
		assertThat(Character.class,
				allOf(hasValidGettersAndSetters(), hasValidBeanConstructor()));
	}

}
