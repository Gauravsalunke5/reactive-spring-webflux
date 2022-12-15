package com.reactivespring.repository;

import com.reactivespring.domain.MovieInfo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.List;

public interface MovieInfoRepository extends ReactiveMongoRepository<MovieInfoRepository, String> {
    Flux<Object> saveAll(List<MovieInfo> movieinfos);
}
