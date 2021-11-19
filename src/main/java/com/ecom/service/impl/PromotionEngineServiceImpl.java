package com.ecom.service.impl;

import com.ecom.bo.OrderRequest;
import com.ecom.bo.OrderResponse;
import com.ecom.entity.Promotion;
import com.ecom.repository.ItemDetailRepository;
import com.ecom.repository.PromotionRepository;
import com.ecom.service.PromotionEngineService;
import com.ecom.util.Discount;
import com.ecom.util.impl.Default;
import com.ecom.util.impl.FixedDiscount;
import com.ecom.util.impl.PercentageDiscount;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class PromotionEngineServiceImpl implements PromotionEngineService {

    private ItemDetailRepository itemDetailRepository;
    private PromotionRepository promotionRepository;

    public PromotionEngineServiceImpl(ItemDetailRepository itemDetailRepository, PromotionRepository promotionRepository) {
        this.itemDetailRepository = itemDetailRepository;
        this.promotionRepository = promotionRepository;
    }

    @Override
    public OrderResponse order(OrderRequest orderRequest) {
        AtomicReference<Integer> total = new AtomicReference<>(0);
        promotionRepository.findAll().stream().forEach(promotion -> {
            List products = orderRequest.getProducts().stream()
                    .filter(product -> promotion.getSkuId().contains(product.getSkuId()))
                    .collect(Collectors.toList());
            Discount discount = getDiscout(promotion);
            total.set(total.get() + discount.applyDiscount(products));
        });
        return OrderResponse.builder().products(orderRequest.getProducts()).total(total.get()).build();
    }

    private Discount getDiscout(Promotion promotion){
        if(promotion.getDiscountType().equals("FIXED")){
            return new FixedDiscount(promotion);
        } else if(promotion.getDiscountType().equals("PERCENTAGE")){
            return new PercentageDiscount(promotion);
        } else {
            return new Default();
        }
    }
}
