package com.ecom.service.impl;

import com.ecom.bo.*;
import com.ecom.entity.ItemDetail;
import com.ecom.entity.Promotion;
import com.ecom.repository.ItemDetailRepository;
import com.ecom.repository.PromotionRepository;
import com.ecom.service.PromotionEngineService;
import com.ecom.util.Discount;
import com.ecom.util.impl.Default;
import com.ecom.util.impl.FixedDiscount;
import com.ecom.util.impl.PercentageDiscount;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

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
        List<Product> products = new ArrayList<>(orderRequest.getProducts());
        promotionRepository.findAll().stream().forEach(promotion -> {
            Discount discount = getDiscout(promotion);
            total.set(total.get() + discount.applyDiscount(products));
        });
        Discount discount = getDiscout(null);
        total.set(total.get() + discount.applyDiscount(products));
        return OrderResponse.builder().products(orderRequest.getProducts()).total(total.get()).build();
    }

    @Override
    public String addPromotion(PromotionRequest promotionRequest) {
        Random rand = new Random();
        if (promotionRequest != null) {
            promotionRepository.add(Promotion.builder().fixedPrice(promotionRequest.getFixedPrice())
                    .discountRule(promotionRequest.getDiscountRule()).discountType(promotionRequest.getDiscountType())
                    .count(promotionRequest.getCount()).skuId(promotionRequest.getSkuId())
                    .percentage(promotionRequest.getPercentage()).id(String.valueOf(rand.nextInt())).build());
            return "Success";
        }
        return "Failed";

    }

    @Override
    public String addItem(ItemRequest itemRequest) {
        if (itemRequest != null) {
            itemDetailRepository.add(ItemDetail.builder().skuId(itemRequest.getSkuId())
                    .price(itemRequest.getPrice()).build());
            return "Success";
        }
        return "Failed";
    }

    private Discount getDiscout(Promotion promotion) {
        if (promotion != null && promotion.getDiscountType().equals("FIXED")) {
            return new FixedDiscount(promotion, itemDetailRepository);
        } else if (promotion != null && promotion.getDiscountType().equals("PERCENTAGE")) {
            return new PercentageDiscount(promotion, itemDetailRepository);
        } else {
            return new Default(itemDetailRepository);
        }
    }
}
