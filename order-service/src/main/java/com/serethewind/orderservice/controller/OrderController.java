package com.serethewind.orderservice.controller;

import com.serethewind.orderservice.dto.OrderRequest;
import com.serethewind.orderservice.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompletionStage<String> placeOrder(@RequestBody OrderRequest orderRequest){
       return orderService.placeOrder(orderRequest);

    }
}
