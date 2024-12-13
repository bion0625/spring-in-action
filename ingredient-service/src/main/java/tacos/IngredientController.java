package tacos;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IngredientController {
	@GetMapping("/ingredient/{id}")
	public Ingredient get(@PathVariable String id) {
		return new Ingredient(id, "this is " + id);
	}
}
