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
}