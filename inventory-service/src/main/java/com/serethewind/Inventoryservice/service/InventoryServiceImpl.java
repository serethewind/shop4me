package com.serethewind.Inventoryservice.service;

import com.serethewind.Inventoryservice.dto.InventoryResponse;
import com.serethewind.Inventoryservice.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {
    private InventoryRepository inventoryRepository;

    @Override
    @Transactional(readOnly = true)
    @SneakyThrows //Thread.sleep throws an exception that is caught by sneaky throw (don't use in production)
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        log.info("Wait started");
        Thread.sleep(30000); //Simulating a slow service by causing a 10-second sleep
        log.info("Wait ended");
        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map((inventory) ->
                        InventoryResponse.builder()
                                .skuCode(inventory.getSkuCode())
                                .isInStock(inventory.getQuantity() > 0)
                                .build()
                ).collect(Collectors.toList());
    }
}
