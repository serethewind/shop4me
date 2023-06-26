package com.serethewind.productservice.service;

import com.serethewind.productservice.dto.ProductRequest;
import com.serethewind.productservice.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    void createProduct(ProductRequest productRequest);

    List<ProductResponse> getAllProducts();
}
