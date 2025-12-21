package com.lv.api.dto;

import com.lv.api.service.util.Constants;
import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Serdeable
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {
    private Long id;
    private String name;
    private Integer quantity;

//    these come from Unit class
    private String unit;

    public void renameUnitToPlural() {
        this.unit = unit + Constants.PLURAL_ITEM;
    }
}
