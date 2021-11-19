package com.ecom.service;

import com.ecom.bo.OrderRequest;
import com.ecom.bo.OrderResponse;
import com.ecom.bo.Product;
import com.ecom.repository.ItemDetailRepository;
import com.ecom.repository.PromotionRepository;
import com.ecom.service.impl.PromotionEngineServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class PromotionEnginerServiceTest {

    @InjectMocks
    private PromotionEngineServiceImpl promotionEngineService;

    @Mock
    private ItemDetailRepository itemDetailRepository =  new ItemDetailRepository();
    @Mock
    private PromotionRepository promotionRepository = new PromotionRepository();

    @Before
    public void before(){
        promotionEngineService =  new PromotionEngineServiceImpl(itemDetailRepository, promotionRepository);
    }

    @Test
    public void itemListWithoutPromotion(){
        OrderRequest orderRequest = OrderRequest.builder().build();
        OrderResponse response = promotionEngineService.order(orderRequest);
        Assert.assertEquals(100, response.getTotal().intValue());
    }

    @Test
    public void itemListWithIndividualPromotion(){
        OrderRequest orderRequest = OrderRequest.builder().products(new ArrayList<>()).build();
        orderRequest.getProducts().add(Product.builder().skuId("A").count(5).build());
        orderRequest.getProducts().add(Product.builder().skuId("B").count(3).build());
        orderRequest.getProducts().add(Product.builder().skuId("C").count(1).build());
        orderRequest.getProducts().add(Product.builder().skuId("D").count(1).build());
        OrderResponse response = promotionEngineService.order(orderRequest);
        Assert.assertEquals(130, response.getTotal().intValue());
    }

    @Test
    public void itemListWithComboPromotion(){
        OrderRequest orderRequest = OrderRequest.builder().products(new ArrayList<>()).build();
        orderRequest.getProducts().add(Product.builder().skuId("A").count(5).build());
        orderRequest.getProducts().add(Product.builder().skuId("B").count(3).build());
        orderRequest.getProducts().add(Product.builder().skuId("C").count(1).build());
        orderRequest.getProducts().add(Product.builder().skuId("D").count(1).build());
        OrderResponse response = promotionEngineService.order(orderRequest);
        Assert.assertEquals(370, response.getTotal().intValue());
    }

    @After
    public void after(){

    }
}
