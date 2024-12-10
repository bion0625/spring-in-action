package tacos.rest;

import org.springframework.web.client.RestTemplate;

public class Delete {
	private RestTemplate rest = new RestTemplate();

	public static void main(String[] args) {
		Delete app = new Delete();
		app.deleteIngredient("FLTO");
	}
	
	public void deleteIngredient(String ingredientId) {
		rest.delete("http://localhost:8080/api/ingredients/{id}", ingredientId);
	}
}
