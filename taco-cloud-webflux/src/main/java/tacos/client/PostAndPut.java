package tacos.client;

import org.jgroups.util.UUID;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import tacos.Ingredient;
import tacos.Ingredient.Type;

@Slf4j
public class PostAndPut {
	
	public static void main(String[] args) {
		PostAndPut app = new PostAndPut(WebClient.create("http://localhost:8080"));
		app.post();
		app.put(new Ingredient("FLTO", "Flour Tortilla - Modify", Type.PROTEIN));
	}
	
	private WebClient webClient;
	
	public PostAndPut(WebClient webClient) {
		this.webClient = webClient;
	}
	
	public void post() {
		Mono<Ingredient> ingredientMono = Mono.just(testIngredient());
		Mono<Ingredient> result = webClient
		.post()
		.uri("/api/ingredients")
//		.body(ingredientMono, Ingredient.class)
		.bodyValue(testIngredient())
		.retrieve()
		.bodyToMono(Ingredient.class);
		
//		result.subscribe(i -> {});
		
		result.doOnNext(i -> log.info(i.toString())).block();
	}
	
	public void put(Ingredient ingredient) {
		Mono<Void> result = webClient
		.put()
		.uri("/api/ingredients/{id}", ingredient.getId())
		.bodyValue(ingredient)
		.retrieve()
		.bodyToMono(Void.class);
		
		result.doOnNext(i -> log.info(i.toString())).block();
	}

	private Ingredient testIngredient() {
		Ingredient ingredient = new Ingredient();
		ingredient.setId(UUID.randomUUID().toString());
		ingredient.setName(UUID.randomUUID().toString());
		ingredient.setType(Type.CHEESE);
		return ingredient;
	}
}
