package tacos.rest;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;

@Slf4j
public class Get {
	
	private RestTemplate rest = new RestTemplate();
	
	public static void main(String[] args) {
		Get app = new Get();
		Ingredient flto = app.getIngredientById("FLTO");
		log.info(flto.toString());
	}
	
	public Ingredient getIngredientById(String ingredientId) {
//		return rest.getForObject("http://localhost:8080/api/ingredients/{id}", Ingredient.class, ingredientId);

//		Map<String, String> urlVariables = new HashMap<>();
//		urlVariables.put("id", ingredientId);
//		return rest.getForObject("http://localhost:8080/api/ingredients/{id}", Ingredient.class, urlVariables);
		
//		Map<String, String> urlVariables = new HashMap<>();
//		urlVariables.put("id", ingredientId);
//		URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/api/ingredients/{id}").build(urlVariables);
//		return rest.getForObject(url, Ingredient.class);
		
		ResponseEntity<Ingredient> responseEntity = rest.getForEntity("http://localhost:8080/api/ingredients/{id}", Ingredient.class, ingredientId);
		log.info("Fetched time: " + responseEntity.getHeaders().getDate());
		return responseEntity.getBody();
	}

}
