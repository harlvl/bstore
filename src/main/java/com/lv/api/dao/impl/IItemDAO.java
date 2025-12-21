package com.lv.api.dao.impl;

import com.lv.api.dto.ItemDTO;
import com.lv.api.dto.ItemsProviderDTO;

import java.util.List;
import java.util.Optional;

public interface IItemDAO {
    Optional<ItemDTO> findById(Long id);
    Optional<ItemDTO> save(ItemDTO dto);
    List<ItemsProviderDTO> findItemsProvider(Long itemId);
}
