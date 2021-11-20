package com.ecom.service;

import com.ecom.bo.ItemRequest;
import com.ecom.bo.OrderRequest;
import com.ecom.bo.OrderResponse;
import com.ecom.bo.PromotionRequest;

public interface PromotionEngineService {
    OrderResponse order(OrderRequest orderRequest);

    String addPromotion(PromotionRequest promotionRequest);

    String addItem(ItemRequest itemRequest);
}
