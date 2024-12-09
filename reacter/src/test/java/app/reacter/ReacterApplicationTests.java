package app.reacter;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import reactor.core.publisher.Flux;
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

}
