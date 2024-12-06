package tacos.web.api;

import java.util.Iterator;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import tacos.Taco;
import tacos.data.TacoRepository;

@RestController
@RequestMapping(path = "/design", produces = "application/json")
@CrossOrigin(origins = "*")
public class ApiDesignTacoController {
	private final TacoRepository tacoRepo;
	
//	EntityLinks
	
	public ApiDesignTacoController(TacoRepository tacoRepo) {
		this.tacoRepo = tacoRepo;
	}
	
	@GetMapping("/recent")
	public CollectionModel<EntityModel<Taco>> recentTacos() {
		PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());

		Iterable<Taco> tacos = tacoRepo.findAll(page).getContent();
		CollectionModel<EntityModel<Taco>> recentCollectionModel = CollectionModel.wrap(tacos);
		
		recentCollectionModel.add(
//				Link.of("http://localhost:8080/design/recent", "recents"));

//				WebMvcLinkBuilder.linkTo(ApiDesignTacoController.class)
//				.slash("recent")
//				.withRel("recents"));
				
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ApiDesignTacoController.class).recentTacos())
				.withRel("recents"));
		return recentCollectionModel;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Taco> tacoById(@PathVariable Long id) {
		return tacoRepo.findById(id)
				.map(taco -> new ResponseEntity<>(taco, HttpStatus.OK))
				.orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
	}
	
	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Taco postTaco(@RequestBody Taco taco) {
		return tacoRepo.save(taco);
	}
}
