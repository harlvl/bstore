package com.lv.api.service.impl;

import com.lv.api.dao.impl.IItemDAO;
import com.lv.api.dto.ItemDTO;
import com.lv.api.dto.ItemsProviderDTO;
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
    public Optional<ItemDTO> findById(Long id) {
        Optional<ItemDTO> o = itemDAO.findById(id);
        if (o.isPresent()) {
            ItemDTO dto = o.get();
            if (dto.getQuantity() != null && dto.getQuantity() > 1) {
                dto.renameUnitToPlural();
            }

            return Optional.of(dto);
        }

        return Optional.empty();
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
