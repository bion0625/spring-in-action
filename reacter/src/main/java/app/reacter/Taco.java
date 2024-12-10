package app.reacter;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import lombok.Data;

@Data
@Entity
public class Taco {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Date createdAt;

	private String name;
	
	@ManyToMany(targetEntity = Ingredient.class)
	private List<Ingredient> ingredients;
	
	@PrePersist
	void createdAt() {
		this.createdAt = new Date();
	}
}
