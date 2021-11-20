package com.ecom.service;

import com.ecom.PromotionEngineApplication;
import com.ecom.bo.OrderRequest;
import com.ecom.bo.OrderResponse;
import com.ecom.bo.Product;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PromotionEngineApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PromotionEngineServiceIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void givenOrder_whenIndividualPromotion() throws Exception {
        OrderRequest request = OrderRequest.builder().products(new ArrayList<>()).build();
        request.getProducts().add(Product.builder().skuId("A").count(1).build());
        request.getProducts().add(Product.builder().skuId("B").count(1).build());
        request.getProducts().add(Product.builder().skuId("C").count(1).build());

        ResponseEntity<OrderResponse> response = this.restTemplate
                .postForEntity("http://localhost:" + port + "/promotion-engine/v1/order", request, OrderResponse.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(100, response.getBody().getTotal().intValue());
    }

    @Test
    public void givenOrder_whenIndividualAndComboPromotion() throws Exception {
        OrderRequest request = OrderRequest.builder().products(new ArrayList<>()).build();
        request.getProducts().add(Product.builder().skuId("A").count(5).build());
        request.getProducts().add(Product.builder().skuId("B").count(5).build());
        request.getProducts().add(Product.builder().skuId("C").count(1).build());
        request.getProducts().add(Product.builder().skuId("D").count(1).build());

        ResponseEntity<OrderResponse> response = this.restTemplate
                .postForEntity("http://localhost:" + port + "/promotion-engine/v1/order", request, OrderResponse.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(380, response.getBody().getTotal().intValue());
    }

    @Test
    public void givenOrder_whenDefaultAndIndividualPromotion() throws Exception {
        OrderRequest request = OrderRequest.builder().products(new ArrayList<>()).build();
        request.getProducts().add(Product.builder().skuId("A").count(5).build());
        request.getProducts().add(Product.builder().skuId("B").count(5).build());
        request.getProducts().add(Product.builder().skuId("C").count(1).build());

        ResponseEntity<OrderResponse> response = this.restTemplate
                .postForEntity("http://localhost:" + port + "/promotion-engine/v1/order", request, OrderResponse.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(370, response.getBody().getTotal().intValue());
    }

    @Test
    public void givenOrder_whenIndividualAndMultiComboPromotion() throws Exception {
        OrderRequest request = OrderRequest.builder().products(new ArrayList<>()).build();
        request.getProducts().add(Product.builder().skuId("A").count(5).build());
        request.getProducts().add(Product.builder().skuId("B").count(5).build());
        request.getProducts().add(Product.builder().skuId("C").count(2).build());
        request.getProducts().add(Product.builder().skuId("D").count(3).build());

        ResponseEntity<OrderResponse> response = this.restTemplate
                .postForEntity("http://localhost:" + port + "/promotion-engine/v1/order", request, OrderResponse.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(425, response.getBody().getTotal().intValue());
    }
}
