package com.example.streaming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@SpringBootApplication
@RestController
public class StreamingApplication {

    public static void main(String[] args) {
        SpringApplication.run(StreamingApplication.class, args);
    }

    @GetMapping(value = "/hello", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Greeting> hello() {
        Flux<Greeting> greetings = Flux.empty();

        for (int i = 1; i < 5; i++) {
            greetings = greetings.concatWith(createMono(i));
        }

        return greetings;
    }

    private Mono<Greeting> createMono(int i) {
        return Mono.fromCallable(() -> new Greeting(Integer.toString(i), (i * 1000)));
    }

    public class Greeting {

        private String message;

        public Greeting() {
        }

        public Greeting(String message) {
            this.message = message;
        }

        public Greeting(String message, int delay) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.message = message;
        }

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }
}
