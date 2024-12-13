package tacos;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import reactor.core.publisher.Mono;

@RestController
public class IngredientController {
	
//	private RestTemplate rest;
//	private IngredientServiceClient client;
	private IngredientClient client;
	
//	public IngredientController(RestTemplate rest) {
//		this.rest = rest;
//	}
	
//	public IngredientController(IngredientServiceClient client) {
//		this.client = client;
//	}
	
	public IngredientController(IngredientClient client) {
		this.client = client;
	}

	@GetMapping("/")
//	public Mono<String> string() {
	public Ingredient string() {
//		return rest.getForObject("http://ingredient-service/ingredient/{id}", String.class, "RestTemplate");
//		return client.getString();
		return client.getString("Feign");
	}
}
