package tacos.web.api;

import java.util.Date;
import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tacos.Taco;

@AllArgsConstructor
public class TacoResource {
	@Getter
	private Date createdAt;

	@Getter
	private String name;
	
	@Getter
	private List<EntityModel<IngredientResource>> ingredients;
	
	static EntityModel<TacoResource> fromEntity(Taco entity) {
		return EntityModel.of(new TacoResource(
				entity.getCreatedAt(), 
				entity.getName(), 
				entity.getIngredients().stream().map(e -> IngredientResource.fromEntity(e)).toList()), 
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ApiDesignTacoController.class).tacoById(entity.getId())).withSelfRel());
	}
}
