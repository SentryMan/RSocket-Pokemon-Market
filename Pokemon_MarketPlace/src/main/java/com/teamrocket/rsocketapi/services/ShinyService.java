package com.teamrocket.rsocketapi.services;

import java.math.BigInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.teamrocket.rsocketapi.dto.ShinyDTO;
import com.teamrocket.rsocketapi.mappers.ShinyMapper;
import com.teamrocket.rsocketapi.model.ShinyPokemon;
import com.teamrocket.rsocketapi.repository.ShinyPokeDatabase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ShinyService {

  @Autowired private final ShinyPokeDatabase repo;
  private final ShinyMapper mapper;

  public Flux<ShinyDTO> getAll() {

    return repo.findAll().map(mapper::toDTO);
  }

  public Mono<ShinyPokemon> findById(BigInteger id) {
    return repo.findById(id);
  }

  public Mono<ShinyDTO> save(ShinyDTO poke) {
    final ShinyPokemon entity = mapper.toEntity(poke);
    return repo.insert(entity).map(mapper::toDTO);
  }

  public Mono<ShinyDTO> update(ShinyDTO poke) {

    return repo.existsById(poke.getId())
        .filter(b -> b)
        .map(b -> poke)
        .map(mapper::toEntity)
        .flatMap(repo::save)
        .map(mapper::toDTO);
  }

  public Mono<Void> deleteById(BigInteger id) {
    return repo.deleteById(id);
  }
}
