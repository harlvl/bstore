package com.lv.api.dao.impl;

import com.lv.api.dto.ItemDTO;
import com.lv.api.model.Item;

import java.util.Optional;

public interface IItemDAO {
    Optional<Item> findById(Long id);
    Optional<ItemDTO> save(ItemDTO dto);
}
