package com.ecom.service;

import com.ecom.bo.OrderRequest;
import com.ecom.bo.OrderResponse;
import com.ecom.bo.Product;
import com.ecom.entity.ItemDetail;
import com.ecom.entity.Promotion;
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
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class PromotionEnginerServiceTest {

    @InjectMocks
    private PromotionEngineServiceImpl promotionEngineService;

    @Mock
    private ItemDetailRepository itemDetailRepository = new ItemDetailRepository();
    @Mock
    private PromotionRepository promotionRepository = new PromotionRepository();

    @Before
    public void before() {
        List<Promotion> promotions = new ArrayList<>();
        promotions.add(Promotion.builder().skuId(Arrays.asList("A")).count(3).discountType("FIXED")
                .discountRule("INDIVIDUAL").fixedPrice(130).build());
        promotions.add(Promotion.builder().skuId(Arrays.asList("B")).count(2).discountType("FIXED")
                .discountRule("INDIVIDUAL").fixedPrice(45).build());
        promotions.add(Promotion.builder().skuId(Arrays.asList("C", "D")).count(1).discountType("FIXED")
                .discountRule("COMBO").fixedPrice(30).build());
        Mockito.when(promotionRepository.findAll()).thenReturn(promotions);
        Mockito.when(itemDetailRepository.findById(Mockito.matches("A")))
                .thenReturn(ItemDetail.builder().skuId("A").price(50).build());
        Mockito.when(itemDetailRepository.findById(Mockito.matches("B")))
                .thenReturn(ItemDetail.builder().skuId("B").price(30).build());
        Mockito.when(itemDetailRepository.findById(Mockito.matches("C")))
                .thenReturn(ItemDetail.builder().skuId("C").price(20).build());
        Mockito.when(itemDetailRepository.findById(Mockito.matches("D")))
                .thenReturn(ItemDetail.builder().skuId("D").price(15).build());
        promotionEngineService = new PromotionEngineServiceImpl(itemDetailRepository, promotionRepository);
    }

    @Test
    public void itemListWithoutPromotion() {
        OrderRequest orderRequest = OrderRequest.builder().products(new ArrayList<>()).build();
        orderRequest.getProducts().add(Product.builder().skuId("A").count(1).build());
        orderRequest.getProducts().add(Product.builder().skuId("B").count(1).build());
        orderRequest.getProducts().add(Product.builder().skuId("C").count(1).build());
        OrderResponse response = promotionEngineService.order(orderRequest);
        Assert.assertEquals(100, response.getTotal().intValue());
    }

    @Test
    public void itemListWithIndividualPromotion() {
        OrderRequest orderRequest = OrderRequest.builder().products(new ArrayList<>()).build();
        orderRequest.getProducts().add(Product.builder().skuId("A").count(5).build());
        orderRequest.getProducts().add(Product.builder().skuId("B").count(5).build());
        orderRequest.getProducts().add(Product.builder().skuId("C").count(1).build());
        orderRequest.getProducts().add(Product.builder().skuId("D").count(1).build());
        OrderResponse response = promotionEngineService.order(orderRequest);
        Assert.assertEquals(380, response.getTotal().intValue());
    }

    @Test
    public void itemListWithComboPromotion() {
        OrderRequest orderRequest = OrderRequest.builder().products(new ArrayList<>()).build();
        orderRequest.getProducts().add(Product.builder().skuId("A").count(5).build());
        orderRequest.getProducts().add(Product.builder().skuId("B").count(5).build());
        orderRequest.getProducts().add(Product.builder().skuId("C").count(1).build());
        OrderResponse response = promotionEngineService.order(orderRequest);
        Assert.assertEquals(370, response.getTotal().intValue());
    }

    @After
    public void after() {

    }
}
