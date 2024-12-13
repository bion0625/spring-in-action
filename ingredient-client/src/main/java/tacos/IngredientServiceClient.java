//package tacos;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.client.WebClient;
//
//import reactor.core.publisher.Mono;
//
//@Component
//public class IngredientServiceClient {
//
//	private WebClient.Builder webClientBuilder;
//	
//	public IngredientServiceClient(WebClient.Builder webClientBuilder) {
//		this.webClientBuilder = webClientBuilder;
//	}
//	
//	public Mono<String> getString() {
//		return webClientBuilder.build()
//				.get()
//				.uri("http://ingredient-service/ingredient/{id}", "WebClient")
//				.retrieve().bodyToMono(String.class);
//	}
//}
