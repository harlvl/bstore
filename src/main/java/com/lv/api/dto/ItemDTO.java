package com.lv.api.dto;

import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @Serdeable @NoArgsConstructor @AllArgsConstructor
public class ItemDTO {
    private Long id;
    private String name;
    private Integer quantity;
}
