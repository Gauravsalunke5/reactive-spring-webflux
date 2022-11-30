package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple4;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

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

    public Flux<String> nameMono_flatMapMany(int stringLength) {
        return Mono.just("Gaurav")
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMapMany(this::splitString);
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

    public Flux<String> namesFlux_transform(int stringLength) {
        Function<Flux<String>, Flux<String>> filterMap = name -> name.map(String::toUpperCase)
                .filter(s -> s.length() > stringLength);
        return Flux.fromIterable(List.of("Gaurav", "G2", "g3"))// db or remove service
                .transform(filterMap)
                .flatMap(s -> splitString(s))//"G","A","U","R","A","V","G","2","G","3"
                .defaultIfEmpty("default")
                .log();
    }

    public Flux<String> namesFlux_transform_switchIfEmpty(int stringLength) {
        Function<Flux<String>, Flux<String>> filterMap = name -> name.map(String::toUpperCase)
                .filter(s -> s.length() > stringLength).flatMap(s -> splitString(s));
        var defaultFlux = Flux.just("default").transform(filterMap);
        return Flux.fromIterable(List.of("Gaurav", "G2", "g3"))// db or remove service
                .transform(filterMap)
                .switchIfEmpty(defaultFlux)
                .log();
    }

    public Flux<String> explore_concat() {
        var abcFlux = Flux.just("A", "B", "C");
        var dedFlux = Flux.just("D", "E", "F");
        return Flux.concat(abcFlux, dedFlux).log();
    }

    public Flux<String> explore_concatWith() {
        var abcFlux = Flux.just("A", "B", "C");
        var dedFlux = Flux.just("D", "E", "F");
        return abcFlux.concatWith(dedFlux).log();
    }

    public Flux<String> explore_concatWith_mono() {
        var aMono = Mono.just("A");
        var bMono = Mono.just("B");
        return aMono.concatWith(bMono).log();
    }

    public Flux<String> explore_merge() {
        var abcFlux = Flux.just("A", "B", "C").
                delayElements(Duration.ofMillis(100));
        var dedFlux = Flux.just("D", "E", "F")
                .delayElements(Duration.ofMillis(120));
        return Flux.merge(abcFlux, dedFlux).log();
    }

    public Flux<String> explore_merge_with() {
        var abcFlux = Flux.just("A", "B", "C").
                delayElements(Duration.ofMillis(100));
        var dedFlux = Flux.just("D", "E", "F")
                .delayElements(Duration.ofMillis(120));
        return abcFlux.mergeWith(dedFlux).log();
    }

    public Flux<String> explore_mergeWith_mono() {
        var aMono = Mono.just("A");
        var bMono = Mono.just("B");
        return aMono.mergeWith(bMono).log();
    }

    public Flux<String> explore_merge_sequential() {
        var abcFlux = Flux.just("A", "B", "C").
                delayElements(Duration.ofMillis(100));
        var dedFlux = Flux.just("D", "E", "F")
                .delayElements(Duration.ofMillis(120));
        return Flux.mergeSequential(abcFlux,dedFlux).log();
    }

    public Flux<String> explore_zip() {
        var abcFlux = Flux.just("A", "B", "C");

        var dedFlux = Flux.just("D", "E", "F");
        return Flux.zip(abcFlux,dedFlux,(first,second)->first+second).log();//AD,BE,CF
    }

    public Flux<String> explore_zip1() {
        var abcFlux = Flux.just("A", "B", "C");
        var dedFlux = Flux.just("D", "E", "F");
        var _123Flux =Flux.just("1","2","3");
        var _456Flux =Flux.just("4","5","6");

        return Flux.zip(abcFlux,dedFlux,_123Flux,_456Flux)
                .map(t4->t4.getT1()+t4.getT2()+t4.getT3()+t4.getT4()).log();
    }

    public Flux<String> explore_zipWith() {
        var abcFlux = Flux.just("A", "B", "C");

        var dedFlux = Flux.just("D", "E", "F");
        return abcFlux.zipWith(dedFlux,(first,second)->first+second).log();//AD,BE,CF
    }
    public Mono<String> explore_zipWith_mono() {
        var aMono = Mono.just("A");
        var bMono = Mono.just("B");
        return aMono.zipWith(bMono).map(t2->t2.getT1()+t2.getT2()).log();
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
