package com.ecom.util.impl;

import com.ecom.bo.Product;
import com.ecom.entity.Promotion;
import com.ecom.repository.ItemDetailRepository;
import com.ecom.util.Discount;
import lombok.AllArgsConstructor;

import java.util.List;


public class PercentageDiscount implements Discount {

    private Promotion promotion;
    private ItemDetailRepository itemDetailRepository;

    public PercentageDiscount(Promotion promotion) {
        this.promotion = promotion;
    }

    @Override
    public Integer applyDiscount(List<Product> products) {
        return null;
    }
}
