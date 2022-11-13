package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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

    public Flux<String> namesFlux_map(int stringLength) {
        // filter the string whose length greater than 3
        return Flux.fromIterable(List.of("Gaurav", "G2", "g3"))// db or remove service
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .map(s-> s.length() +"-"+s) //6-GAURAV
                .log();
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
