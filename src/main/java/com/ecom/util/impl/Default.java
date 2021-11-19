package com.ecom.util.impl;

import com.ecom.bo.Product;
import com.ecom.entity.ItemDetail;
import com.ecom.entity.Promotion;
import com.ecom.repository.ItemDetailRepository;
import com.ecom.util.Discount;
import lombok.AllArgsConstructor;

import java.util.List;

public class Default implements Discount {

    private ItemDetailRepository itemDetailRepository;

    public Default(ItemDetailRepository itemDetailRepository) {
        this.itemDetailRepository = itemDetailRepository;
    }

    @Override
    public Integer applyDiscount(List<Product> products) {
        return products.stream().mapToInt(product -> {
            ItemDetail itemDetail = itemDetailRepository.findById(product.getSkuId());
            return itemDetail.getPrice() * product.getCount();
        }).sum();
    }
}
