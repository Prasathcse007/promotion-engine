package com.ecom.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Promotion {
    private String id;
    private List<String> skuId;
    private String discountType;
    private String discountRule;
    private Integer fixedPrice;
    private Integer percentage;
    private Integer count;
}
