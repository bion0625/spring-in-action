package tacos;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Mono;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Taco;
import tacos.data.TacoRepository;
import tacos.web.api.ApiDesignTacoController;

class DesignTacoControllerTest {

	@Test
	void shouldReturnRecentTacos() throws IOException {
		List<Taco> tacos = List.of(
				testTaco(1L), testTaco(2L),
				testTaco(3L), testTaco(4L),
				testTaco(5L), testTaco(6L),
				testTaco(7L), testTaco(8L),
				testTaco(9L), testTaco(10L),
				testTaco(11L), testTaco(12L),
				testTaco(13L), testTaco(14L),
				testTaco(15L), testTaco(16L));
		
		TacoRepository tacoRepo = Mockito.mock(TacoRepository.class);
		when(tacoRepo.findAll()).thenReturn(tacos);
		
		WebTestClient testClient = WebTestClient.bindToController(
				new ApiDesignTacoController(tacoRepo))
				.build();
		
		testClient.get().uri("/design/recent")
		.exchange()
		.expectStatus().isOk()
		.expectBody()
		.jsonPath("$").isArray()
		.jsonPath("$").isNotEmpty()
		.jsonPath("$[0].id").isEqualTo(tacos.get(0).getId().toString())
		.jsonPath("$[0].name").isEqualTo("Taco 1")
		.jsonPath("$[1].id").isEqualTo(tacos.get(1).getId().toString())
		.jsonPath("$[1].name").isEqualTo("Taco 2")
		.jsonPath("$[11].id").isEqualTo(tacos.get(11).getId().toString())
		.jsonPath("$[11].name").isEqualTo("Taco 12")
		.jsonPath("$[12]").doesNotExist();
	}
	
	private Taco testTaco(Long nubmer) {
		Taco taco = new Taco();
		taco.setId(nubmer);
		taco.setName("Taco " + nubmer);
		List<Ingredient> ingredients = new ArrayList<>();
		ingredients.add(new Ingredient("INGA", "Ingredient A", Type.WRAP));
		ingredients.add(new Ingredient("INGB", "Ingredient B", Type.PROTEIN));
		taco.setIngredients(ingredients);
		return taco;
	}
	
	@Test
	void shouldSaveTaco() {
		TacoRepository tacoRepo = Mockito.mock(TacoRepository.class);
		Mono<Taco> unsavedTaco = Mono.just(testTaco(null));
		Taco savedTaco = testTaco(null);
		savedTaco.setId(1L);
		
		when(tacoRepo.save(any())).thenReturn(savedTaco);
		
		WebTestClient testClient = WebTestClient.bindToController(
				new ApiDesignTacoController(tacoRepo)).build();
		
		testClient.post()
		.uri("/design")
		.contentType(MediaType.APPLICATION_JSON)
		.body(unsavedTaco, Taco.class)
		.exchange()
		.expectStatus().isCreated()
		.expectBody(Taco.class)
		.isEqualTo(savedTaco);
	}

}
