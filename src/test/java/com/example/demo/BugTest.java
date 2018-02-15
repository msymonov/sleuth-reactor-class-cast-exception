package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class BugTest {

    @Test(expected = ClassCastException.class)
    public void shouldReproduceClassCastExceptionIssue() {
        process()
            .as(StepVerifier::create)
            .expectError(ClassCastException.class)
            .verify();
    }

    private static Mono<String> process() {
        return Mono.just("value1")
            .flatMap(request ->
                Mono.just("value2")
                    .then(Mono.empty())
            )
            .map(list -> "value3")
            .switchIfEmpty(Mono.just("value4"));
    }
}
