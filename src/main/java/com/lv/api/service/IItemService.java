package com.lv.api.service;

import com.lv.api.dto.ItemDTO;
import com.lv.api.model.Item;

import java.util.Optional;

public interface IItemService {
    Optional<Item> findById(Long id);
    Optional<ItemDTO> save(ItemDTO dto);
}
