package com.serethewind.Inventoryservice.controller;

import com.serethewind.Inventoryservice.service.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
@AllArgsConstructor
public class InventoryController {

    private InventoryService inventoryService;

    @GetMapping("/{skuCode}")
    public ResponseEntity<Boolean> isInStock(@PathVariable("skuCode") String skuCode) {
        return ResponseEntity.ok(inventoryService.isInStock(skuCode));
    }
}
