package com.ecom.service;

import com.ecom.bo.OrderRequest;
import com.ecom.bo.OrderResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PromotionEnginerServiceTest {

    @InjectMocks
    private PromotionEngineService promotionEngineService;

    @Before
    public void before(){

    }

    @Test
    public void itemListWithoutPromotion(){
        OrderRequest orderRequest = OrderRequest.builder().build();
        OrderResponse response = promotionEngineService.order(orderRequest);
        Assert.assertEquals(100, response.getDiscount());
    }

    @Test
    public void itemListWithIndividualPromotion(){
        OrderRequest orderRequest = OrderRequest.builder().build();
        OrderResponse response = promotionEngineService.order(orderRequest);
        Assert.assertEquals(130, response.getDiscount());
    }

    @Test
    public void itemListWithComboPromotion(){
        OrderRequest orderRequest = OrderRequest.builder().build();
        OrderResponse response = promotionEngineService.order(orderRequest);
        Assert.assertEquals(370, response.getDiscount());
    }

    @After
    public void after(){

    }
}
