package app.reacter;

import java.net.URI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import app.reacter.data.TacoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public class RouterFunctionConfig {
	
	private TacoRepository tacoRepo;
	
	public RouterFunctionConfig(TacoRepository tacoRepo) {
		this.tacoRepo = tacoRepo;
	}

	@Bean
	public RouterFunction<?> helloRouterFunction() {
//		return RouterFunctions.route(RequestPredicates.GET("/hello"), 
//				request -> ServerResponse.ok().body(Mono.just("Hello World!"), String.class))
//				.andRoute(RequestPredicates.GET("/bye"), 
//				request -> ServerResponse.ok().body(Mono.just("See ya!"), String.class));
		return RouterFunctions.route(RequestPredicates.GET("/design/taco"), this::recents)
				.andRoute(RequestPredicates.POST("/design"), this::postTaco);
	}
	
	public Mono<ServerResponse> recents(ServerRequest request) {
		return ServerResponse.ok()
				.body(Flux.fromIterable(tacoRepo.findAll()).take(12), Taco.class);
	}
	public Mono<ServerResponse> postTaco(ServerRequest request) {
		return request
				.bodyToMono(Taco.class)
				.map(t -> tacoRepo.save(t))
				.flatMap(savedTaco -> ServerResponse.created(
						URI.create("http://localhost:8080/design/taco/" + savedTaco.getId()))
				.bodyValue(savedTaco));
	}
}
