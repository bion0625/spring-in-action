package tacos.rest;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;
import tacos.Ingredient.Type;

@Slf4j
public class Post {
	private RestTemplate rest = new RestTemplate();

	public static void main(String[] args) {
		Post app = new Post();

//		Ingredient ingredient = app.createIngredient(new Ingredient("ADD", "NAME", Type.PROTEIN));
//		log.info("ingredient: " + ingredient);
		
//		URI url = app.createIngredient(new Ingredient("ADD", "NAME", Type.PROTEIN));
//		log.info("url: " + url);
		
		Ingredient ingredient =  app.createIngredient(new Ingredient("ADD", "NAME", Type.PROTEIN));
		log.info("ingredient: " + ingredient);
	}
	
//	public Ingredient createIngredient(Ingredient ingredient) {
//		return rest.postForObject("http://localhost:8080/api/ingredients", ingredient, Ingredient.class);
//	}
	
//	public URI createIngredient(Ingredient ingredient) {
//		return rest.postForLocation("http://localhost:8080/api/ingredients", ingredient);
//	}
	
	public Ingredient createIngredient(Ingredient ingredient) {
		ResponseEntity<Ingredient> responseEntity = rest.postForEntity("http://localhost:8080/api/ingredients", ingredient, Ingredient.class);
		log.info("New resource created at " + responseEntity.getHeaders().getLocation());
		return responseEntity.getBody();
	}
}
