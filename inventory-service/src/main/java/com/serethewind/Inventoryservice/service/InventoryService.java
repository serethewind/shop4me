package com.serethewind.Inventoryservice.service;

import com.serethewind.Inventoryservice.dto.InventoryResponse;

import java.util.List;

public interface InventoryService {

    List<InventoryResponse> isInStock(List<String> skuCode);
}
