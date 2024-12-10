package app.reacter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Taco_Order")
public class Order implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Date placedAt;

	private String deliveryName;
	
	private String deliveryStreet;
	
	private String deliveryCity;
	
	private String deliveryState;
	
	private String deliveryZip;
	
	private String ccNumber;

	private String ccExpiration;
	
	private String ccCVV;
	
	@ManyToMany(targetEntity = Taco.class)
	private List<Taco> tacos = new ArrayList<>();
	
	@ManyToOne
	Users users;
	
	public void addDesign(Taco design) {
		tacos.add(design);
	}
	
	@PrePersist
	void placedAt() {
		this.placedAt = new Date();
	}
}
