package tacos.web.api;

import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import tacos.data.TacoRepository;

@RepositoryRestController
public class RecentTacoController {
	private final TacoRepository tacoRepo;
	
	public RecentTacoController(TacoRepository tacoRepo) {
		this.tacoRepo = tacoRepo;
	}
	
	@GetMapping(path = "/tacos/recent", produces = "application/hal+json")
	public ResponseEntity<CollectionModel<EntityModel<TacoResource>>> recentTacos() {
		PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());

		Iterable<EntityModel<TacoResource>> tacos = tacoRepo.findAll(page).getContent().stream()
				.map(TacoResource::fromEntity).collect(Collectors.toList());
		CollectionModel<EntityModel<TacoResource>> recentCollectionModel = CollectionModel.of(tacos);
		
		return new ResponseEntity<>(recentCollectionModel, HttpStatus.OK);
	}
}
