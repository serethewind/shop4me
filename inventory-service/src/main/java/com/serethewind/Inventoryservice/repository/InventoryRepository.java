package com.serethewind.Inventoryservice.repository;

import com.serethewind.Inventoryservice.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {
//    @Query("select i from inventory_database i where i.skuCode = ?1")
//    Optional<InventoryEntity> findBySkuCode(String skuCode);
    InventoryEntity existsBySkuCode(String skuCode);

    @Query("select i from inventory_database i where i.skuCode in ?1")
    List<InventoryEntity> findBySkuCodeIn(List<String> skuCode);




}
