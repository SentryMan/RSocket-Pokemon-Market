package com.teamrocket.rsocketapi.controllers;

import java.math.BigInteger;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.teamrocket.rsocketapi.dto.ItemDTO;
import com.teamrocket.rsocketapi.services.ItemService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ItemController {

  private final ItemService service;

  @GetMapping("/api/item/{id}")
  public Mono<ItemDTO> getByID(@PathVariable BigInteger id) {
    return service.findById(id);
  }

  @PostMapping("/api/item")
  public Mono<ItemDTO> post(@RequestBody ItemDTO item) {

    return service.save(item);
  }

  @PutMapping("/api/item")
  public Mono<ItemDTO> update(@RequestBody ItemDTO item) {
    return service.update(item);
  }

  @DeleteMapping("/api/item/{id}")
  public Mono<Void> deleteByID(@PathVariable BigInteger id) {

    return service.deleteById(id);
  }
}
