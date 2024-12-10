package app.reacter.data;

import org.springframework.data.repository.CrudRepository;

import app.reacter.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
