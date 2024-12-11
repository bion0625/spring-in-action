package tacos.client;

import java.time.Duration;

import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tacos.Ingredient;

@Slf4j
public class Get {
	
	public static void main(String[] args) {
//		Get app = new Get();
		Get app = new Get(WebClient.create("http://localhost:8080"));
		app.get("FLTO");
//		app.getAll();
	}
	
	private WebClient webClient;
	
	public Get(WebClient webClient) {
		this.webClient = webClient;
	}

	public void get(String ingredientId) {
//		Mono<Ingredient> ingredient = WebClient.create()
//		.get()
//		.uri("http://localhost:8080/api/ingredients/{id}", ingredientId)
//		.retrieve()
//		.bodyToMono(Ingredient.class);
		
		Mono<Ingredient> ingredient = webClient
		.get()
		.uri("/api/ingredients/{id}", ingredientId)
		.retrieve()
		.bodyToMono(Ingredient.class);
		
//		ingredient.doOnNext(i -> log.info(ingredientId + ": " + i)).block();
		ingredient
		.timeout(Duration.ofSeconds(1))
		.subscribe(
				i -> {
					log.info(ingredientId + ": " + i);
					}, 
				e -> {
					log.info(ingredientId + " error : " + e);
					});
	}
	
	public void getAll() {
		Flux<Ingredient> ingredients = WebClient.create()
		.get()
		.uri("http://localhost:8080/api/ingredients")
		.retrieve()
		.bodyToFlux(Ingredient.class);
		
		log.info("" + ingredients.blockFirst());
		ingredients.doOnNext(i -> log.info("all: " + i)).collectList().block();
	}
}
