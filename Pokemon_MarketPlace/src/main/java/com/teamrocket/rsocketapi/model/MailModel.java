package com.teamrocket.rsocketapi.model;

import java.util.List;
import lombok.Data;

@Data
public class MailModel {

  String emailAddress;
  private List<Pokemon> poke;
  private List<Item> item;
  private int total;
}
