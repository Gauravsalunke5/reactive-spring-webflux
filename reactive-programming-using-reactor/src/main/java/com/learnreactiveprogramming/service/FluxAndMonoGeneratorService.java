package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class FluxAndMonoGeneratorService {
    public Flux<String> namesFlux() {
        return Flux.fromIterable(List.of("Gaurav", "G2", "g3")).log(); // db or remove service
    }

    public Mono<String> nameMono() {
        return Mono.just("GAURAV");
    }

    public Flux<String> namesFlux_map() {
        return Flux.fromIterable(List.of("Gaurav", "G2", "g3"))// db or remove service
                .map(String::toUpperCase)
                // .map(s->s.toUpperCase())
                .log();
    }

    public Flux<String> namesFlux_immutability() {
        var namesFlux = Flux.fromIterable(List.of("Gaurav", "G2", "g3"));// db or remove service
        namesFlux.map(String::toUpperCase);
        return namesFlux;
    }

    public Mono<List<String>> nameMono_flatMap(int stringLength) {
        return Mono.just("Gaurav")
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMap(this::splitMono);
    }

    private Mono<List<String>> splitMono(String s) {
        var charArray = s.split("");
        var charList = List.of(charArray);
        return Mono.just(charList);
    }

    public Flux<String> namesFlux_map(int stringLength) {
        // filter the string whose length greater than 3
        return Flux.fromIterable(List.of("Gaurav", "G2", "g3"))// db or remove service
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .map(s -> s.length() + "-" + s) //6-GAURAV
                .log();
    }

    public Flux<String> namesFlux_flatmap(int stringLength) {
        // filter the string whose length greater than 3
        return Flux.fromIterable(List.of("Gaurav", "G2", "g3"))// db or remove service
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMap(s -> splitString(s))//"G","A","U","R","A","V","G","2","G","3"
                .log();
    }

    public Flux<String> namesFlux_flatmap_async(int stringLength) {
        // filter the string whose length greater than 3
        return Flux.fromIterable(List.of("Gaurav", "G2", "g3"))// db or remove service
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMap(s -> splitStringWithDelay(s))//"G","A","U","R","A","V","G","2","G","3"
                .log();
    }

    public Flux<String> namesFlux_concatmap(int stringLength) {
        // filter the string whose length greater than 3
        return Flux.fromIterable(List.of("Gaurav", "G2", "g3"))// db or remove service
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .concatMap(s -> splitStringWithDelay(s))//"G","A","U","R","A","V","G","2","G","3"
                .log();
    }

    public Flux<String> splitString(String name) {
        var charArray = name.split("");
        return Flux.fromArray(charArray);
    }

    public Flux<String> splitStringWithDelay(String name) {
        var charArray = name.split("");
        var delay = new Random().nextInt(1000);
        return Flux.fromArray(charArray).delayElements(Duration.ofMillis(delay));
    }

    public static void main(String[] args) {
        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
        fluxAndMonoGeneratorService.namesFlux().subscribe(name -> {
            System.out.println("Flux Name is " + name);
        });

        fluxAndMonoGeneratorService.nameMono().subscribe(name -> {
            System.out.println("Mono Name is " + name);
        });
    }
}
