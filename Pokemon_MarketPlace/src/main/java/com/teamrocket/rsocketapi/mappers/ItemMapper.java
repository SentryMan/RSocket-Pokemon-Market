package com.teamrocket.rsocketapi.mappers;

import org.springframework.stereotype.Component;
import com.teamrocket.rsocketapi.dto.ItemDTO;
import com.teamrocket.rsocketapi.model.Item;

@Component
public class ItemMapper {

  public Item toEntity(ItemDTO dto) {
    final Item item = new Item();

    item.setName(dto.getName());
    item.setPrice(dto.getPrice());
    item.setDescription(dto.getDescription());
    item.setImage(dto.getImage());
    return item;
  }

  public ItemDTO toDTO(Item entity) {
    final ItemDTO item = new ItemDTO();
    item.setId(entity.getId());
    item.setName(entity.getName());
    item.setPrice(entity.getPrice());
    item.setDescription(entity.getDescription());
    item.setImage(entity.getImage());
    return item;
  }
}
