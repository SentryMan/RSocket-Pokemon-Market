package com.teamrocket.rsocketapi.services;

import java.math.BigInteger;
import org.springframework.stereotype.Service;
import com.teamrocket.rsocketapi.dto.ItemDTO;
import com.teamrocket.rsocketapi.mappers.ItemMapper;
import com.teamrocket.rsocketapi.model.Item;
import com.teamrocket.rsocketapi.repository.ItemRepo;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ItemService {

  private final ItemRepo repo;
  private final ItemMapper mapper;

  public Flux<ItemDTO> getAll() {

    return repo.findAll().map(mapper::toDTO);
  }

  public Mono<ItemDTO> findById(BigInteger id) {
    return repo.findById(id).map(mapper::toDTO);
  }

  public Mono<ItemDTO> save(ItemDTO Item) {
    final Item entity = mapper.toEntity(Item);
    return repo.insert(entity).map(mapper::toDTO);
  }

  public Mono<ItemDTO> update(ItemDTO Item) {

    return repo.existsById(Item.getId())
        .filter(b -> b)
        .map(b -> Item)
        .map(mapper::toEntity)
        .flatMap(repo::save)
        .map(mapper::toDTO);
  }

  public Mono<Void> deleteById(BigInteger id) {
    return repo.deleteById(id);
  }
}
