package com.teamrocket.rsocketapi.mappers;

import org.springframework.stereotype.Component;
import com.teamrocket.rsocketapi.dto.PokemonDTO;
import com.teamrocket.rsocketapi.model.Pokemon;

@Component
public class PokemonMapper {

  public Pokemon toEntity(PokemonDTO dto) {
    final Pokemon pokemon = new Pokemon();

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

  public PokemonDTO toDTO(Pokemon entity) {
    final PokemonDTO pokemon = new PokemonDTO();
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
