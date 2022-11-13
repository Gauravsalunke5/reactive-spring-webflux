package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

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
}