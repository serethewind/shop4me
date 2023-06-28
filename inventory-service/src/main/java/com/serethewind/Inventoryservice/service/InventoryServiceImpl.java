package com.serethewind.Inventoryservice.service;

import com.serethewind.Inventoryservice.dto.InventoryResponse;
import com.serethewind.Inventoryservice.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InventoryServiceImpl implements InventoryService{
    private InventoryRepository inventoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCode) {
      return inventoryRepository.findBySkuCodeIn(skuCode).stream()
              .map((inventory) ->
                      InventoryResponse.builder()
                      .skuCode(inventory.getSkuCode())
                      .isInStock(inventory.getQuantity() > 0)
                      .build()
              ).collect(Collectors.toList());
    }
}
