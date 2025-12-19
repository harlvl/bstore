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
public class Provider {
    private Long id;
    private String name;
    private String description;
    private String ruc;
    private String phoneNumber;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
