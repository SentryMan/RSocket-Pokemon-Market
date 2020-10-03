package com.teamrocket.rsocketapi.controllers;

import java.math.BigInteger;
import java.net.URISyntaxException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.teamrocket.rsocketapi.dto.PokemonDTO;
import com.teamrocket.rsocketapi.model.Pokemon;
import com.teamrocket.rsocketapi.services.PokeService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class PokeController {

  private final PokeService service;

  @GetMapping("/api/pokemon/{id}")
  public Mono<Pokemon> getByID(@PathVariable BigInteger id) {
    return service.findById(id);
  }

  @PostMapping("/api/pokemon")
  public Mono<PokemonDTO> post(@RequestBody PokemonDTO pokemon) throws URISyntaxException {

    return service.save(pokemon);
  }

  @PutMapping("/api/pokemon")
  public Mono<PokemonDTO> update(@RequestBody PokemonDTO pokemon) {
    return service.update(pokemon);
  }

  @DeleteMapping("/api/pokemon/{id}")
  public Mono<Void> deleteByID(@PathVariable BigInteger id) {

    return service.deleteById(id);
  }
}
