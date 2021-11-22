package com.ecom.util;

import com.ecom.bo.Product;
import com.ecom.entity.ItemDetail;
import com.ecom.repository.ItemDetailRepository;
import com.ecom.util.impl.Default;
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
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class DefaultTest {

    @InjectMocks
    private Default defaultCal;

    @Mock
    private ItemDetailRepository itemDetailRepository = new ItemDetailRepository();

    @Before
    public void before() {
        Mockito.when(itemDetailRepository.findById(Mockito.matches("A")))
                .thenReturn(ItemDetail.builder().skuId("A").price(50).build());
    }

    @Test
    public void itemListWithIndividualPromotion() {
        defaultCal = new Default(itemDetailRepository);
        List<Product> products = new ArrayList<>();
        products.add(Product.builder().skuId("A").count(2).build());
        Integer response = defaultCal.applyDiscount(products);
        Assert.assertEquals(100, response.intValue());
    }

    @After
    public void after() {

    }
}
