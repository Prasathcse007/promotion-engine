package com.ecom.util.impl;

import com.ecom.bo.Product;
import com.ecom.entity.ItemDetail;
import com.ecom.entity.Promotion;
import com.ecom.repository.ItemDetailRepository;
import com.ecom.util.Discount;

import java.util.List;
import java.util.stream.Collectors;


public class PercentageDiscount implements Discount {

    private Promotion promotion;
    private ItemDetailRepository itemDetailRepository;

    public PercentageDiscount(Promotion promotion, ItemDetailRepository itemDetailRepository) {
        this.promotion = promotion;
        this.itemDetailRepository = itemDetailRepository;
    }

    @Override
    public Integer applyDiscount(List<Product> products) {
        List<Product> productList = products.stream().filter(product ->
                promotion.getSkuId().contains(product.getSkuId())).collect(Collectors.toList());
        if(promotion.getDiscountRule().equals("INDIVIDUAL")){
            products.removeAll(productList);
            return productList.stream().mapToInt(product -> {
                ItemDetail itemDetail = itemDetailRepository.findById(product.getSkuId());
                return (((product.getCount() / promotion.getCount()) *  itemDetail.getPrice() * 100)
                        / promotion.getPercentage()) + ((product.getCount() % promotion.getCount())
                        * itemDetail.getPrice());
            }).sum();
        } else {
            if(productList.size() == promotion.getSkuId().size()) {
                products.removeAll(productList);
                int min = productList.stream().mapToInt(value -> value.getCount()).min().getAsInt();
                return productList.stream().mapToInt(product -> {
                    ItemDetail itemDetail = itemDetailRepository.findById(product.getSkuId());
                    return (product.getCount() - min) * itemDetail.getPrice();
                }).sum() + (( min * promotion.getFixedPrice() * 100)
                        / promotion.getPercentage());
            }
            return 0;
        }
    }
}
