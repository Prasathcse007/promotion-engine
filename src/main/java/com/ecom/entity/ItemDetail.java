package com.ecom.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ItemDetail {
    private String skuId;
    private Integer price;
}
