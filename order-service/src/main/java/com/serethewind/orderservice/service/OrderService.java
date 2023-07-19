package com.serethewind.orderservice.service;

import com.serethewind.orderservice.dto.OrderRequest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public interface OrderService {

    String placeOrder(OrderRequest orderRequest);

    String fallbackMethod(OrderRequest orderRequest, Throwable throwable);
}
