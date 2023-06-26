package com.serethewind.Inventoryservice.service;

import com.serethewind.Inventoryservice.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class InventoryServiceImpl implements InventoryService{
    private InventoryRepository inventoryRepository;

    @Override
    @Transactional(readOnly = true)
    public Boolean isInStock(String skuCode) {
      return inventoryRepository.findBySkuCode(skuCode).isPresent();

    }
}
