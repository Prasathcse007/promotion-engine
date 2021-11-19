package com.ecom.bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponse {
    private List<Product> products;
    private Integer total;
}
