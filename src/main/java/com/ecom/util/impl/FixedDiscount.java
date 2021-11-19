package com.ecom.util.impl;

import com.ecom.bo.Product;
import com.ecom.util.Discount;

import java.util.List;

public class FixedDiscount implements Discount {
    @Override
    public Integer applyDiscount(List<Product> product) {
        return null;
    }
}
