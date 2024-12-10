package tacos.traverson;

import java.net.URI;
import java.util.Collection;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Taco;

@Slf4j
public class Main {
	public static void main(String[] args) {
		Traverson traverson = new Traverson(URI.create("http://localhost:8080/api"), MediaTypes.HAL_JSON);
		ParameterizedTypeReference<CollectionModel<EntityModel<Ingredient>>> ingredientType = new ParameterizedTypeReference<CollectionModel<EntityModel<Ingredient>>>() {};
		
		CollectionModel<EntityModel<Ingredient>> ingredientRes = traverson.follow("ingredients").toObject(ingredientType);
		
		Collection<EntityModel<Ingredient>> ingredients = ingredientRes.getContent();
		
		log.info("ingredients: " + ingredients);
		
//		ParameterizedTypeReference<CollectionModel<EntityModel<Taco>>> tacoType = new ParameterizedTypeReference<CollectionModel<EntityModel<Taco>>>() {};
//		
////		CollectionModel<EntityModel<Taco>> tacoRes = traverson.follow("tacos").follow("recent").toObject(tacoType);
//		CollectionModel<EntityModel<Taco>> tacoRes = traverson.follow("tacos", "recent").toObject(tacoType);
//		
//		Collection<EntityModel<Taco>> tacos = tacoRes.getContent();
//		
//		log.info("tacos: " + tacos);
		
		Main app = new Main();
		Ingredient ingredient = app.addIngredient(new Ingredient("ADD", "NAME", Type.PROTEIN));
		log.info("ingredient: " + ingredient);
	}
	
	private Ingredient addIngredient(Ingredient ingredient) {
		Traverson traverson = new Traverson(URI.create("http://localhost:8080/api"), MediaTypes.HAL_JSON);
		RestTemplate rest = new RestTemplate();
		
		String ingredientsUrl = traverson.follow("ingredients").asLink().getHref();
		return rest.postForObject(ingredientsUrl, ingredient, Ingredient.class);
	}
}
