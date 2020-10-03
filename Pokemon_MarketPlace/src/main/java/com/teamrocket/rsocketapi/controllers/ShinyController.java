package com.teamrocket.rsocketapi.controllers;

import java.math.BigInteger;
import java.net.URISyntaxException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.teamrocket.rsocketapi.dto.ShinyDTO;
import com.teamrocket.rsocketapi.model.ShinyPokemon;
import com.teamrocket.rsocketapi.services.ShinyService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ShinyController {

  private final ShinyService service;

  @GetMapping("/api/shiny/{id}")
  public Mono<ShinyPokemon> getByID(@PathVariable BigInteger id) {
    return service.findById(id);
  }

  @PostMapping("/api/shiny")
  public Mono<ShinyDTO> post(@RequestBody ShinyDTO shiny) throws URISyntaxException {

    return service.save(shiny);
  }

  @PutMapping("/api/shiny")
  public Mono<ShinyDTO> update(@RequestBody ShinyDTO shiny) {
    return service.update(shiny);
  }

  @DeleteMapping("/api/shiny/{id}")
  public ResponseEntity<Void> deleteByID(@PathVariable BigInteger id) {

    service.deleteById(id);
    return ResponseEntity.ok().build();
  }
}
