package com.teamrocket.rsocketapi.mappers;

import org.springframework.stereotype.Component;
import com.teamrocket.rsocketapi.dto.ShinyDTO;
import com.teamrocket.rsocketapi.model.ShinyPokemon;

@Component
public class ShinyMapper {

  public ShinyPokemon toEntity(ShinyDTO dto) {
    final ShinyPokemon pokemon = new ShinyPokemon();

    pokemon.setName(dto.getName());
    pokemon.setNationalNum(dto.getNationalNum());
    pokemon.setName(dto.getName());
    pokemon.setType(dto.getType());
    pokemon.setSprite(dto.getSprite());
    pokemon.setTotal(dto.getTotal());
    pokemon.setHp(dto.getHp());
    pokemon.setAttack(dto.getAttack());
    pokemon.setDefense(dto.getDefense());
    pokemon.setSpeed(dto.getSpeed());

    return pokemon;
  }

  public ShinyDTO toDTO(ShinyPokemon entity) {
    final ShinyDTO pokemon = new ShinyDTO();
    pokemon.setId(entity.getId());
    pokemon.setName(entity.getName());
    pokemon.setNationalNum(entity.getNationalNum());
    pokemon.setName(entity.getName());
    pokemon.setType(entity.getType());
    pokemon.setSprite(entity.getSprite());
    pokemon.setTotal(entity.getTotal());
    pokemon.setHp(entity.getHp());
    pokemon.setAttack(entity.getAttack());
    pokemon.setDefense(entity.getDefense());
    pokemon.setSpeed(entity.getSpeed());
    return pokemon;
  }
}
