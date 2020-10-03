package com.teamrocket.rsocketapi.model;

import java.math.BigInteger;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(collection = "items")
@NoArgsConstructor
public class Item {

  @Id private BigInteger id;
  private String name;
  private Integer price;
  private String description;
  private String image;
}
