package com.lv.api.service.impl;

import com.lv.api.dao.impl.IItemDAO;
import com.lv.api.dto.ItemDTO;
import com.lv.api.dto.ItemsProviderDTO;
import com.lv.api.model.Item;
import com.lv.api.service.IItemService;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@Singleton
public class ItemService implements IItemService {
    private final IItemDAO itemDAO;

    public ItemService(IItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    @Override
    public Optional<Item> findById(Long id) {
        return itemDAO.findById(id);
    }

    @Override
    public Optional<ItemDTO> save(ItemDTO dto) {
        return itemDAO.save(dto);
    }

    @Override
    public List<ItemsProviderDTO> findItemsProvider(Long itemId) {
        return itemDAO.findItemsProvider(itemId);
    }
}
