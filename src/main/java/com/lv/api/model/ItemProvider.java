package com.lv.api.model;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Serdeable
@Getter
@Setter
public class ItemProvider {
    private Long itemId;
    private Long providerId;

    private Double cost;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
