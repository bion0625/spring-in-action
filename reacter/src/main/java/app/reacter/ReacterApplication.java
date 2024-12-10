package app.reacter;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import app.reacter.Ingredient.Type;

@SpringBootApplication
public class ReacterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReacterApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner dataLoader(app.reacter.data.IngredientRepository repo) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				List<Ingredient> ingredients = Arrays.asList(
						new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
						new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
						new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
						new Ingredient("CARN", "Carnitas", Type.PROTEIN),
						new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
						new Ingredient("LETC", "Lettuce", Type.VEGGIES),
						new Ingredient("CHED", "Cheddar", Type.CHEESE),
						new Ingredient("JACK", "Monterrey", Type.CHEESE),
						new Ingredient("SLSA", "Salsa", Type.SAUCE),
						new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
						);
				repo.saveAll(ingredients);
			}
		};
	}

}