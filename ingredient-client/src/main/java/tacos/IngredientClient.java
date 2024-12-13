package tacos;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("ingredient-service")
public interface IngredientClient {
	@GetMapping("ingredient/{id}")
	Ingredient getString(@PathVariable("id") String id);
}
