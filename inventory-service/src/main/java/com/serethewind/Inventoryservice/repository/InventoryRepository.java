package com.serethewind.Inventoryservice.repository;

import com.serethewind.Inventoryservice.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {

   List<InventoryEntity> findBySkuCodeIn(List<String> skuCode);

}
