package com.ecom.util.impl;

import com.ecom.bo.Product;
import com.ecom.entity.ItemDetail;
import com.ecom.entity.Promotion;
import com.ecom.repository.ItemDetailRepository;
import com.ecom.util.Discount;
import lombok.AllArgsConstructor;

import java.util.List;


public class FixedDiscount implements Discount {

    private Promotion promotion;
    private ItemDetailRepository itemDetailRepository;

    public FixedDiscount(Promotion promotion) {
        this.promotion = promotion;
    }

    @Override
    public Integer applyDiscount(List<Product> products) {
        return null;
    }
}
