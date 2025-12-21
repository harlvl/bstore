package com.lv.api.model;

import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@Serdeable
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private Long id;
    private String name;
    private String description;
    private Integer quantity;
    private Double price;

//    FK
    private Long unitId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
