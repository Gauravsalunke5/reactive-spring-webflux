package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FluxAndMonoGeneratorServiceTest {

    FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

    @Test
    void namesFlux() {
        // given

        // when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux();
        //then
        StepVerifier.create(namesFlux)
                // .expectNext("Gaurav", "G2", "g3")
                //.expectNextCount(3)
                .expectNext("Gaurav").expectNextCount(2).verifyComplete();
    }

    @Test
    void namesFlux_map() {
        {
            // given

            // when
            var namesFlux = fluxAndMonoGeneratorService.namesFlux_map();
            //then
            StepVerifier.create(namesFlux)
                    .expectNext("GAURAV", "G2", "G3")
                    .verifyComplete();
        }
    }

    @Test
    void namesFlux_immutability() {
        // given

        // when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_immutability();
        //then
        StepVerifier.create(namesFlux)
                .expectNext("Gaurav", "G2", "g3")
                .verifyComplete();
    }

    @Test
    void testNamesFlux_map() {
        //given
        int stringLength = 3;
        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_map(stringLength);
        //then
        StepVerifier.create(namesFlux)
                .expectNext("6-GAURAV")
                .verifyComplete();
    }

    @Test
    void namesFlux_flatmap() {
        {
            //given
            int stringLength = 1;
            //when
            var namesFlux = fluxAndMonoGeneratorService.namesFlux_flatmap(stringLength);
            //then
            StepVerifier.create(namesFlux)
                    .expectNext("G", "A", "U", "R", "A", "V", "G", "2", "G", "3")
                    .verifyComplete();
        }
    }

    @Test
    void namesFlux_flatmap_async() {
        //given
        int stringLength = 1;
        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_flatmap_async(stringLength);
        //then
        StepVerifier.create(namesFlux)
                //.expectNext("G", "A", "U", "R", "A", "V", "G", "2", "G", "3")
                .expectNextCount(10)
                .verifyComplete();
    }

    @Test
    void namesFlux_concatmap() {
        //given
        int stringLength = 1;
        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_concatmap(stringLength);
        //then
        StepVerifier.create(namesFlux)
                .expectNext("G", "A", "U", "R", "A", "V", "G", "2", "G", "3")
                .verifyComplete();
    }

    @Test
    void nameMono_flatMap() {
        {
            //given
            int stringLength = 1;
            //when
            var namesMono = fluxAndMonoGeneratorService.nameMono_flatMap(stringLength);
            //then
            StepVerifier.create(namesMono)
                    .expectNext(List.of("G", "A", "U", "R", "A", "V"))
                    .verifyComplete();
        }
    }

    @Test
    void nameMono_flatMapMany() {
        {
            //given
            int stringLength = 1;
            //when
            var namesMono = fluxAndMonoGeneratorService.nameMono_flatMapMany(stringLength);
            //then
            StepVerifier.create(namesMono)
                    .expectNext("G", "A", "U", "R", "A", "V")
                    .verifyComplete();
        }
    }

    @Test
    void namesFlux_transform() {

        //given
        int stringLength = 1;
        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_transform(stringLength);
        //then
        StepVerifier.create(namesFlux)
                .expectNext("G", "A", "U", "R", "A", "V", "G", "2", "G", "3")
                .verifyComplete();

    }

    @Test
    void namesFlux_transform_1() {

        //given
        int stringLength = 6;
        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_transform(stringLength);
        //then
        StepVerifier.create(namesFlux)
                .expectNext("default")
                .verifyComplete();

    }

    @Test
    void namesFlux_transform_switchIfEmpty() {

        //given
        int stringLength = 6;
        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_transform_switchIfEmpty(stringLength);
        //then
        StepVerifier.create(namesFlux)
                .expectNext("D", "E", "F", "A", "U", "L", "T")
                .verifyComplete();

    }

    @Test
    void explore_concat() {
        var concatFlux = fluxAndMonoGeneratorService.explore_concat();

        StepVerifier.create(concatFlux)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    void explore_merge() {
        var concatFlux = fluxAndMonoGeneratorService.explore_merge();

        StepVerifier.create(concatFlux)
                .expectNext("A", "D", "B", "E", "C", "F")
                .verifyComplete();
    }

    @Test
    void explore_mergeSequential() {
        var concatFlux = fluxAndMonoGeneratorService.explore_merge_sequential();

        StepVerifier.create(concatFlux)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    void explore_zip() {
        var concatFlux = fluxAndMonoGeneratorService.explore_zip();

        StepVerifier.create(concatFlux)
                .expectNext("AD", "BE", "CF")
                .verifyComplete();
    }

    @Test
    void explore_zipWith() {
        var concatFlux = fluxAndMonoGeneratorService.explore_zip1();

        StepVerifier.create(concatFlux)
                .expectNext("AD14", "BE25", "CF36")
                .verifyComplete();
    }
}