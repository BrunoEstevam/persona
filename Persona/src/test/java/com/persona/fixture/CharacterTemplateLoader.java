package com.persona.fixture;

import com.persona.dto.CharacterRequest;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class CharacterTemplateLoader implements TemplateLoader {

	@Override
	public void load() {
		Fixture.of(CharacterRequest.class).addTemplate("characterRequest", new Rule() {
			{
				add("name", random("MeuNome", "Char", "racter"));
				add("active", random(false, true));
			}
		});
	}

}
