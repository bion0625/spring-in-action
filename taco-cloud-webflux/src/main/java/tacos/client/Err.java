package tacos.client;

import java.time.Duration;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import tacos.Ingredient;

@Slf4j
public class Err {
	
	public static void main(String[] args) {
		Err app = new Err(WebClient.create("http://localhost:8080"));
		app.get("ITEM");
//		app.get("FLTO");
	}
	
	private WebClient webClient;
	
	public Err(WebClient webClient) {
		this.webClient = webClient;
	}

	public void get(String ingredientId) {
		Mono<Ingredient> ingredient = webClient
		.get()
		.uri("/api/ingredients/{id}", ingredientId)
		.retrieve()
//		.onStatus(status -> status.is4xxClientError(), response -> Mono.just(new UnknownIngredientException()))
		.onStatus(status -> status == HttpStatus.NOT_FOUND, response -> Mono.just(new UnknownIngredientException()))
		.bodyToMono(Ingredient.class);
		
		ingredient
		.timeout(Duration.ofSeconds(1))
		.doOnNext(i -> log.info(ingredientId + ": " + i))
		.doOnError(e -> log.info(ingredientId + ": " + e))
		.block();
//		.subscribe(
//				i -> {
//					log.info(ingredientId + ": " + i);
//					}, 
//				e -> {
//					log.info(ingredientId + " error : " + e);
//					});
	}
}
