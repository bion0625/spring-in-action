package tacos.client;

import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

public class Delete {
	
	public static void main(String[] args) {
		Delete app = new Delete(WebClient.create("http://localhost:8080"));
		app.delete("FLTO").block();
	}

	private WebClient webClient;
	
	public Delete(WebClient webClient) {
		this.webClient = webClient;
	}
	
	public Mono<Void> delete(String ingredientId) {
		return webClient
		.delete()
		.uri("/api/ingredients/{id}", ingredientId)
		.retrieve()
		.bodyToMono(Void.class);
	}
}
