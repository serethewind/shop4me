package com.serethewind.orderservice.service;

import com.serethewind.orderservice.dto.OrderRequest;

public interface OrderService {

    void placeOrder(OrderRequest orderRequest);

    String fallbackMethod(OrderRequest orderRequest, RuntimeException runtimeException);
}
