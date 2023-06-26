package com.serethewind.productservice.service;

import com.serethewind.productservice.dto.ProductRequest;
import com.serethewind.productservice.dto.ProductResponse;
import com.serethewind.productservice.model.Product;
import com.serethewind.productservice.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Override
    public void createProduct(ProductRequest productRequest) {
        Product newProduct = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        productRepository.save(newProduct);
        log.info("Product {} is saved", newProduct.getId());

    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> productList = productRepository.findAll();


            return productList.stream().map((product) -> ProductResponse
                .builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build()
        ).collect(Collectors.toList());
    }
}
