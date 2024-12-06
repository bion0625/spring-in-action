package tacos.rest;

import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;
import tacos.Ingredient.Type;

@Slf4j
public class Put {
	private RestTemplate rest = new RestTemplate();

	public static void main(String[] args) {
		Put app = new Put();
		app.updateIngredient(new Ingredient("FLTO", "Flour Tortilla - MODIFY", Type.WRAP));
	}
	
	public void updateIngredient(Ingredient ingredient) {
		rest.put("http://localhost:8080/api/ingredients/{id}", ingredient, ingredient.getId());
	}

}
