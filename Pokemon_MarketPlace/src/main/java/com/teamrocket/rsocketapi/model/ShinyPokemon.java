package com.teamrocket.rsocketapi.model;

import java.math.BigInteger;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(collection = "shiny-pokemons")
@NoArgsConstructor
public class ShinyPokemon {

  @Id private BigInteger id;

  @Field("national_number")
  private String nationalNum;

  private String name;
  private String[] type;

  @Field("sprites")
  private Sprite sprite;

  private Integer total;
  private Integer hp;
  private Integer attack;
  private Integer defense;
  private Integer speed;
}
