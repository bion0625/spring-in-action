package app.integration;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Order {
	private final String email;
	private List<Taco> tacos = new ArrayList<>();
	
	public void addTaco(Taco taco) {
		tacos.add(taco);
	}
}
