package com.ecom.service;

import com.ecom.bo.OrderRequest;
import com.ecom.bo.OrderResponse;

public interface PromotionEngineService {
    OrderResponse order(OrderRequest orderRequest);
}
