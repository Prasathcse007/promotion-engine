package com.ecom.util;

import com.ecom.bo.OrderRequest;
import com.ecom.bo.OrderResponse;
import com.ecom.bo.Product;
import com.ecom.entity.ItemDetail;
import com.ecom.entity.Promotion;
import com.ecom.repository.ItemDetailRepository;
import com.ecom.repository.PromotionRepository;
import com.ecom.service.impl.PromotionEngineServiceImpl;
import com.ecom.util.impl.FixedDiscount;
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
public class FixedDiscountTest {

    @InjectMocks
    private FixedDiscount fixedDiscount;

    @Mock
    private ItemDetailRepository itemDetailRepository = new ItemDetailRepository();

    @Before
    public void before() {
        List<Promotion> promotions = new ArrayList<>();
        promotions.add(Promotion.builder().skuId(Arrays.asList("A")).count(3).discountType("FIXED")
                .discountRule("INDIVIDUAL").fixedPrice(130).build());
        promotions.add(Promotion.builder().skuId(Arrays.asList("B")).count(2).discountType("FIXED")
                .discountRule("INDIVIDUAL").fixedPrice(45).build());
        promotions.add(Promotion.builder().skuId(Arrays.asList("C", "D")).count(1).discountType("FIXED")
                .discountRule("COMBO").fixedPrice(30).build());
        Mockito.when(itemDetailRepository.findById(Mockito.matches("A")))
                .thenReturn(ItemDetail.builder().skuId("A").price(50).build());
        Mockito.when(itemDetailRepository.findById(Mockito.matches("C")))
                .thenReturn(ItemDetail.builder().skuId("C").price(20).build());
        Mockito.when(itemDetailRepository.findById(Mockito.matches("D")))
                .thenReturn(ItemDetail.builder().skuId("D").price(15).build());
    }

    @Test
    public void itemListWithIndividualPromotion() {
        fixedDiscount = new FixedDiscount(Promotion.builder().skuId(Arrays.asList("A")).count(3).discountType("FIXED")
                .discountRule("INDIVIDUAL").fixedPrice(130).build(), itemDetailRepository);
        List<Product> products = new ArrayList<>();
        products.add(Product.builder().skuId("A").count(5).build());
        products.add(Product.builder().skuId("B").count(5).build());
        Integer response = fixedDiscount.applyDiscount(products);
        Assert.assertEquals(230, response.intValue());
    }

    @Test
    public void itemListWithComboPromotion() {
        fixedDiscount = new FixedDiscount(Promotion.builder().skuId(Arrays.asList("C", "D")).count(1).discountType("FIXED")
                .discountRule("COMBO").fixedPrice(30).build(), itemDetailRepository);
        List<Product> products = new ArrayList<>();
        products.add(Product.builder().skuId("C").count(1).build());
        products.add(Product.builder().skuId("D").count(1).build());
        Integer response = fixedDiscount.applyDiscount(products);
        Assert.assertEquals(30, response.intValue());
    }

    @After
    public void after() {

    }
}
