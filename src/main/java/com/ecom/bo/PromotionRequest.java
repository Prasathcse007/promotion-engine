package com.ecom.bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PromotionRequest {
    private List<String> skuId;
    private String discountType;
    private String discountRule;
    private Integer fixedPrice;
    private Integer percentage;
    private Integer count;
}
