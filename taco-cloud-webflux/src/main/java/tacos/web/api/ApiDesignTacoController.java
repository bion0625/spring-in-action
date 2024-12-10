package tacos.web.api;

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

import reactor.core.publisher.Flux;
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
	public Flux<Taco> recentTacos() {
		return Flux.fromIterable(tacoRepo.findAll()).take(12);
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
