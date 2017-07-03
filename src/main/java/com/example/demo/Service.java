package com.example.demo;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

/**
 * @author lilit on 6/29/17.
 */
@RestController
public class Service {
	@GetMapping(value = "/randomNumbers", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> getReactiveRandomNumbers() {
		return generateRandomNumbers();
	}

	/**
	 * Non-blocking randon number generator
	 */
	public Flux<String> generateRandomNumbers() {
		Flux<Long> interval = Flux.interval(Duration.ofMillis(100));
		Flux<Double> timestamps = Flux.fromStream(Stream.generate(Math::random));
		return Flux.zip(interval, timestamps).map(tp -> new Date().toString() + "   " + tp.getT2() + "\n");
	}
}
