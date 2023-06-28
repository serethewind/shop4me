package com.serethewind.orderservice.service;


import com.serethewind.orderservice.dto.InventoryResponse;
import com.serethewind.orderservice.dto.OrderRequest;
import com.serethewind.orderservice.entity.OrderEntity;
import com.serethewind.orderservice.entity.OrderLineItems;
import com.serethewind.orderservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private WebClient.Builder webClientBuilder;

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

        List<String> skuCodes = newOrder.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).toList();

        /**call the inventory service, and place order, if product is in stock.
         * the return type is a boolean which is a Mono.
         */
        //webClient using the uriBuilder.queryParam will construct the skuCode in this order
        //http://localhost:8082/apoi/inventory?skuCode=iphone-13&skuCode=iphone_14

        InventoryResponse[] inventoryResponseArray =
                webClientBuilder.build().get()
                        .uri("http://inventory-service/api/inventory",
                                uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                        .retrieve()
                        .bodyToMono(InventoryResponse[].class)
                        .block();

        boolean allProductsInStock;
        if (inventoryResponseArray.length == 0) {
            allProductsInStock = false; // Empty array, not all products are in stock
        } else {
            allProductsInStock = Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::isInStock);
            //returns true is there is a total match or false if none matches.
        }

        if (allProductsInStock) {
            orderRepository.save(newOrder);
        } else {
            throw new IllegalArgumentException("Product is not in stock, please try again later");
        }

    }
}
