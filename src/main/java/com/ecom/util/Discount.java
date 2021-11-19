package com.ecom.util;

import com.ecom.bo.Product;

import java.util.List;

public interface Discount {
    public Integer applyDiscount(List<Product> product);
}
