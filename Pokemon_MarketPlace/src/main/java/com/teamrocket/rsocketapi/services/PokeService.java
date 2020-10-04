package com.teamrocket.rsocketapi.services;

import java.math.BigInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.teamrocket.rsocketapi.dto.PokemonDTO;
import com.teamrocket.rsocketapi.mappers.PokemonMapper;
import com.teamrocket.rsocketapi.model.Pokemon;
import com.teamrocket.rsocketapi.repository.PokeRepo;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PokeService {

  @Autowired private final PokeRepo repo;
  private final PokemonMapper mapper;

  public Flux<PokemonDTO> getAll() {

    return repo.findAll().map(mapper::toDTO);
  }

  public Mono<Pokemon> findById(BigInteger id) {
    return repo.findById(id);
  }

  public Mono<PokemonDTO> save(PokemonDTO poke) {
    final Pokemon entity = mapper.toEntity(poke);
    return repo.insert(entity).map(mapper::toDTO);
  }

  public Mono<PokemonDTO> update(PokemonDTO poke) {

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
