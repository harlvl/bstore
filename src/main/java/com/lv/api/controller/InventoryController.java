package com.lv.api.controller;

import com.lv.api.dto.ItemDTO;
import com.lv.api.model.Item;
import com.lv.api.service.IItemService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@ExecuteOn(TaskExecutors.BLOCKING)
@Controller(InventoryController.INVENTORY_PATH)
public class InventoryController {
    public static final String INVENTORY_PATH = "/inventory";

    private final IItemService itemService;

    public InventoryController(IItemService itemService) {
        this.itemService = itemService;
    }

    @Transactional(rollbackFor = {java.lang.Throwable.class})
    @Get("/{id}")
    public HttpResponse<Item> findById(@PathVariable Long id){
        log.info("Get item by id {}", id);
        Optional<Item> item = itemService.findById(id);
        if (item.isEmpty()) {
            return HttpResponse.notFound();
        }

        return HttpResponse.ok(item.get());
    }

    @Transactional(rollbackFor = {java.lang.Throwable.class})
    @Post
    public HttpResponse<ItemDTO> save(@Body ItemDTO dto){
        log.info("Save item {}", dto);
        Optional<ItemDTO> out = itemService.save(dto);
        return out.isEmpty() ? HttpResponse.notFound() : HttpResponse.ok(out.get());
    }
}
