package tacos.web.api;

import org.springframework.hateoas.EntityModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tacos.Ingredient;
import tacos.Ingredient.Type;

@AllArgsConstructor
public class IngredientResource {
	@Getter
	private final String name;
	
	@Getter
	private final Type type;
	
	static EntityModel<IngredientResource> fromEntity(Ingredient entity) {
		return EntityModel.of(new IngredientResource(entity.getName(), entity.getType()));
	}
}
