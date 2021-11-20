package com.ecom.repository;

import com.ecom.entity.Promotion;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class PromotionRepository {
    private List<Promotion> promotions;

    @PostConstruct
    private void init() {
        promotions = new ArrayList<>();
        promotions.add(Promotion.builder().skuId(Arrays.asList("A")).count(3).discountType("FIXED")
                .discountRule("INDIVIDUAL").fixedPrice(130).build());
        promotions.add(Promotion.builder().skuId(Arrays.asList("B")).count(2).discountType("FIXED")
                .discountRule("INDIVIDUAL").fixedPrice(45).build());
        promotions.add(Promotion.builder().skuId(Arrays.asList("C", "D")).count(1).discountType("FIXED")
                .discountRule("COMBO").fixedPrice(30).build());
    }

    public List<Promotion> findAll() {
        return promotions;
    }

    public Promotion add(Promotion promotion) {
        promotions.add(promotion);
        return promotion;
    }
}
