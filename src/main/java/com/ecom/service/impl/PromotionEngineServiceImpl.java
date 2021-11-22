package com.ecom.service.impl;

import com.ecom.bo.*;
import com.ecom.entity.ItemDetail;
import com.ecom.entity.Promotion;
import com.ecom.enums.Constants;
import com.ecom.exception.PromotionException;
import com.ecom.repository.ItemDetailRepository;
import com.ecom.repository.PromotionRepository;
import com.ecom.service.PromotionEngineService;
import com.ecom.util.Discount;
import com.ecom.util.impl.Default;
import com.ecom.util.impl.FixedDiscount;
import com.ecom.util.impl.PercentageDiscount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
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
        try{
            AtomicReference<Integer> total = new AtomicReference<>(0);
            List<Product> products = new ArrayList<>(orderRequest.getProducts());
            promotionRepository.findAll().stream().forEach(promotion -> {
                Discount discount = getDiscout(promotion);
                total.set(total.get() + discount.applyDiscount(products));
            });
            Discount discount = getDiscout(null);
            total.set(total.get() + discount.applyDiscount(products));
            return OrderResponse.builder().products(orderRequest.getProducts()).total(total.get()).build();
        } catch (Exception ex){
            log.error("Error in order : ", ex);
            new PromotionException("Error in order : ", ex);
        }
        return null;
    }

    @Override
    public String addPromotion(PromotionRequest promotionRequest) {
        Random rand = new Random();
        if (promotionRequest != null) {
            promotionRepository.add(Promotion.builder().fixedPrice(promotionRequest.getFixedPrice())
                    .discountRule(promotionRequest.getDiscountRule()).discountType(promotionRequest.getDiscountType())
                    .count(promotionRequest.getCount()).skuId(promotionRequest.getSkuId())
                    .percentage(promotionRequest.getPercentage()).id(String.valueOf(rand.nextInt())).build());
            return Constants.SUCCESS.getValue();
        }
        return Constants.FAILED.getValue();

    }

    @Override
    public String addItem(ItemRequest itemRequest) {
        if (itemRequest != null) {
            itemDetailRepository.add(ItemDetail.builder().skuId(itemRequest.getSkuId())
                    .price(itemRequest.getPrice()).build());
            return Constants.SUCCESS.getValue();
        }
        return Constants.FAILED.getValue();
    }

    private Discount getDiscout(Promotion promotion) {
        if (promotion != null && promotion.getDiscountType().equals(Constants.FIXED.getValue())) {
            return new FixedDiscount(promotion, itemDetailRepository);
        } else if (promotion != null && promotion.getDiscountType().equals(Constants.PERCENTAGE.getValue())) {
            return new PercentageDiscount(promotion, itemDetailRepository);
        } else {
            return new Default(itemDetailRepository);
        }
    }
}
