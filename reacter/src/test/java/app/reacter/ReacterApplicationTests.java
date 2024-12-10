package app.reacter;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

@SpringBootTest
class ReacterApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Test
	void createAFlux_just() {
		Flux<String> fruitFlux = Flux
				.just("Apple", "Orange", "Grape", "Banana", "Strawberry");
//		fruitFlux.subscribe(f -> System.out.println("Here's some fruit: " + f));
		
		StepVerifier.create(fruitFlux)
		.expectNext("Apple")
		.expectNext("Orange")
		.expectNext("Grape")
		.expectNext("Banana")
		.expectNext("Strawberry")
		.verifyComplete();
	}
	
	@Test
	void createAFlux_fromArray() {
		String[] fruits = new String[] {"Apple", "Orange", "Grape", "Banana", "Strawberry"};
		
		Flux fruitFlux = Flux.fromArray(fruits);
		
		StepVerifier.create(fruitFlux)
		.expectNext("Apple")
		.expectNext("Orange")
		.expectNext("Grape")
		.expectNext("Banana")
		.expectNext("Strawberry")
		.verifyComplete();
	}
	
	@Test
	void createAFlux_fromIterable() {
		List<String> fruitList = new ArrayList<>();
		fruitList.add("Apple");
		fruitList.add("Orange");
		fruitList.add("Grape");
		fruitList.add("Banana");
		fruitList.add("Strawberry");
		
		Flux fruitFlux = Flux.fromIterable(fruitList);
		
		StepVerifier.create(fruitFlux)
		.expectNext("Apple")
		.expectNext("Orange")
		.expectNext("Grape")
		.expectNext("Banana")
		.expectNext("Strawberry")
		.verifyComplete();
	}
	
	@Test
	void createAFlux_fromStream() {
		Stream<String> fruitStream = Stream.of("Apple", "Orange", "Grape", "Banana", "Strawberry");
		
		Flux fruitFlux = Flux.fromStream(fruitStream);
		
		StepVerifier.create(fruitFlux)
		.expectNext("Apple")
		.expectNext("Orange")
		.expectNext("Grape")
		.expectNext("Banana")
		.expectNext("Strawberry")
		.verifyComplete();
	}
	
	@Test
	void createAFlux_range() {
		Flux<Integer> intervalFlux = Flux.range(1, 5);
		
		StepVerifier.create(intervalFlux)
		.expectNext(1)
		.expectNext(2)
		.expectNext(3)
		.expectNext(4)
		.expectNext(5)
		.verifyComplete();
	}
	
	@Test
	void ctreateAFlux_interval() {
		Flux<Long> intervalFlux = Flux.interval(Duration.ofSeconds(1)).take(5);
		
		StepVerifier.create(intervalFlux)
		.expectNext(0L)
		.expectNext(1L)
		.expectNext(2L)
		.expectNext(3L)
		.expectNext(4L)
		.verifyComplete();
	}
	
	@Test
	void mergeFluxes() {
		
		Flux<String> characterFlux = Flux
				.just("Gafield", "Kojak", "Barbossa")
				.delayElements(Duration.ofMillis(500));
		Flux<String> foodFlux = Flux
				.just("Lasagna", "Lollipops", "Apples")
				.delaySubscription(Duration.ofMillis(250))
				.delayElements(Duration.ofMillis(500));
		
		Flux<String> mergedFlux = characterFlux.mergeWith(foodFlux);
		
		StepVerifier.create(mergedFlux)
		.expectNext("Gafield")
		.expectNext("Lasagna")
		.expectNext("Kojak")
		.expectNext("Lollipops")
		.expectNext("Barbossa")
		.expectNext("Apples")
		.verifyComplete();
	}
	
	@Test
	void zipFluxes() {
		Flux<String> characterFlux = Flux
				.just("Gafield", "Kojak", "Barbossa");
		Flux<String> foodFlux = Flux
				.just("Lasagna", "Lollipops", "Apples");
		
		Flux<Tuple2<String, String>> zippedFlux = characterFlux.zipWith(foodFlux);
		
		StepVerifier.create(zippedFlux)
		.expectNextMatches(p -> p.getT1().equals("Gafield") && p.getT2().equals("Lasagna"))
		.expectNextMatches(p -> p.getT1().equals("Kojak") && p.getT2().equals("Lollipops"))
		.expectNextMatches(p -> p.getT1().equals("Barbossa") && p.getT2().equals("Apples"))
		.verifyComplete();
	}
	
	@Test
	void zipFluxesToObject() {
		Flux<String> characterFlux = Flux
				.just("Gafield", "Kojak", "Barbossa");
		Flux<String> foodFlux = Flux
				.just("Lasagna", "Lollipops", "Apples");
		
		Flux<String> zippedFlux = Flux.zip(characterFlux, foodFlux, (c, f) -> c + " eats " + f);
		
		StepVerifier.create(zippedFlux)
		.expectNext("Gafield eats Lasagna")
		.expectNext("Kojak eats Lollipops")
		.expectNext("Barbossa eats Apples")
		.verifyComplete();
	}
	
	@Test
	void firstFlux() {
		Flux<String> slowFlux = Flux.just("tortoise", "snail", "sloth").delaySubscription(Duration.ofMillis(100));
		Flux<String> fastFlux = Flux.just("hare", "cheetah", "squirrel");
		
//		Flux<String> firstFlux = Flux.first(slowFlux, fastFlux);
		Flux<String> firstFlux = Flux.firstWithSignal(slowFlux, fastFlux);
		
		StepVerifier.create(firstFlux)
		.expectNext("hare")
		.expectNext("cheetah")
		.expectNext("squirrel")
		.verifyComplete();
	}
	
	@Test
	void skipAFew() {
		Flux<String> skipFlux = Flux.just(
				"one", "two", "skip a few", "ninety nine", "one hundred")
				.skip(3);
		
		StepVerifier.create(skipFlux)
		.expectNext("ninety nine", "one hundred")
		.verifyComplete();
	}
	
	@Test
	void skipAFewSeconds() {
		Flux<String> skipFlux = Flux.just(
				"one", "two", "skip a few", "ninety nine", "one hundred")
				.delayElements(Duration.ofSeconds(1))
				.skip(Duration.ofSeconds(4));
		
		StepVerifier.create(skipFlux)
		.expectNext("ninety nine", "one hundred")
		.verifyComplete();
	}
	
	@Test
	void take() {
		Flux<String> nationalParkFlux = Flux.just(
				"Yellowstone", "Yosemite", "Grand canyon",
				"Zion", "Grand Teton")
				.take(3);
		
		StepVerifier.create(nationalParkFlux)
		.expectNext("Yellowstone", "Yosemite", "Grand canyon")
		.verifyComplete();
	}
	
	@Test
	void takeWithDuration() {
		Flux<String> nationalParkFlux = Flux.just(
				"Yellowstone", "Yosemite", "Grand canyon",
				"Zion", "Grand Teton")
				.delayElements(Duration.ofSeconds(1))
				.take(Duration.ofMillis(3500));
		
		StepVerifier.create(nationalParkFlux)
		.expectNext("Yellowstone", "Yosemite", "Grand canyon")
		.verifyComplete();
	}
	
	@Test
	void filter() {
		Flux<String> nationalParkFlux = Flux.just(
				"Yellowstone", "Yosemite", "Grand canyon",
				"Zion", "Grand Teton")
				.filter(np -> !np.contains(" "));
		
		StepVerifier.create(nationalParkFlux)
		.expectNext("Yellowstone", "Yosemite", "Zion")
		.verifyComplete();
	}
	
	@Test
	void distinct() {
		Flux<String> animalFlux = Flux.just(
				"dog", "cat", "bird", "dog", "bird", "anteater")
				.distinct();
		
		StepVerifier.create(animalFlux)
		.expectNext("dog", "cat", "bird", "anteater")
		.verifyComplete();
	}

	@Test
	void map() {
		Flux<Player> playerFlux = Flux
				.just("Michael Jordan", "Scottie Pippen", "Steven Kerr")
				.map(n -> {
					String[]split = n.split(" ");
					return new Player(split[0], split[1]);
				});
		
		StepVerifier.create(playerFlux)
		.expectNext(new Player("Michael", "Jordan"))
		.expectNext(new Player("Scottie", "Pippen"))
		.expectNext(new Player("Steven", "Kerr"))
		.verifyComplete();
	}
	
	@Test
	void flatMap() {
		Flux<Player> playerFlux = Flux
				.just("Michael Jordan", "Scottie Pippen", "Steven Kerr")
				.flatMap(n -> Mono.just(n)
						.map(p -> {
							String[] split = p.split(" ");
							return new Player(split[0], split[1]);
						})
						.subscribeOn(Schedulers.parallel())
				);
		
		List<Player> playerList = List.of(
				new Player("Michael", "Jordan"),
				new Player("Scottie", "Pippen"),
				new Player("Steven", "Kerr"));
		
		StepVerifier.create(playerFlux)
		.expectNextMatches(p -> playerList.contains(p))
		.expectNextMatches(p -> playerList.contains(p))
		.expectNextMatches(p -> playerList.contains(p))
		.verifyComplete();
	}
	
	@Test
	void buffer() {
		Flux<String> fruitFlux = Flux.just("Apple", "Orange", "Banana", "kiwi", "Strawberry");
		
		Flux<List<String>> bufferedFlux = fruitFlux.buffer(3);
		
		StepVerifier.create(bufferedFlux)
		.expectNext(List.of("Apple", "Orange", "Banana"))
		.expectNext(List.of("kiwi", "Strawberry"))
		.verifyComplete();
		
		Flux
			.just("Apple", "Orange", "Banana", "kiwi", "Strawberry")
			.buffer(3)
			.flatMap(x -> 
				Flux.fromIterable(x)
				.map(y -> y.toUpperCase())
				.subscribeOn(Schedulers.parallel())
				.log())
			.subscribe();
	}
	
	@Test
	void collectList() {
		Flux<String> fruitFlux = Flux.just("Apple", "Orange", "Banana", "kiwi", "Strawberry");
		
		Mono<List<String>> fruitListMono = fruitFlux.collectList();
		
		StepVerifier.create(fruitListMono)
		.expectNext(List.of(
				"Apple", "Orange", "Banana", "kiwi", "Strawberry"))
		.verifyComplete();
	}
	
	@Test
	void collectMap() {
		Flux<String> animalFlux = Flux.just(
				"aardvark", "elephant", "koala", "eagle", "kangaroo");
		
		Mono<Map<Character, String>> animalMapMono = animalFlux.collectMap(a -> a.charAt(0));
		
		StepVerifier
			.create(animalMapMono)
			.expectNextMatches(map -> 
				map.size() == 3 
				&& map.get('a').equals("aardvark") 
				&& map.get('e').equals("eagle") 
				&& map.get('k').equals("kangaroo"))
			.verifyComplete();
	}
}
