package com.serethewind.orderservice.service;


import com.serethewind.orderservice.dto.InventoryResponse;
import com.serethewind.orderservice.dto.OrderRequest;
import com.serethewind.orderservice.entity.OrderEntity;
import com.serethewind.orderservice.entity.OrderLineItems;
import com.serethewind.orderservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private WebClient webClient;

    @Override
    public void placeOrder(OrderRequest orderRequest) {
        List<OrderLineItems> itemsList = orderRequest.getOrderLineItemsDtoList().stream().map(
                (item) -> OrderLineItems.builder()
                        .skuCode(item.getSkuCode())
                        .price(item.getPrice())
                        .quantity(item.getQuantity())
                        .build()
        ).toList();

        OrderEntity newOrder = OrderEntity.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderLineItemsList(itemsList)
                .build();

        List<String> skuCodeList = newOrder.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).toList();

        /**call the inventory service, and place order, if product is in stock.
         * the return type is a boolean which is a Mono.
         */

        Boolean result = webClient.get()
                .uri("http://localhost:8082/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodeList).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        if (result) {
            orderRepository.save(newOrder);
        } else {
            throw new IllegalArgumentException("Product is not in stock, please check back later.");
        }

    }
}
