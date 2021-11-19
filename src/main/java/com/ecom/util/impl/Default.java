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

    @Override
    public Integer applyDiscount(List<Product> products) {
        return products.stream().mapToInt(product -> {
            ItemDetail itemDetail = itemDetailRepository.findById(product.getSkuId());
            if(itemDetail.getPrice() != null) {
                return itemDetail.getPrice() * product.getCount();
            }
            return 0;
        }).sum();
    }
}
