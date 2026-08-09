package com.inventory_service.Inventory_service.repository;

import com.inventory_service.Inventory_service.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {
    @Query(value = "select * from inventory where product_id = :productId", nativeQuery = true)
    public InventoryEntity findByProductId(@Param("productId") String productId);

    @Query(value = "SELECT * FROM inventory WHERE product_id IN (:productIds)", nativeQuery = true)
    List<InventoryEntity> getInventoryEntitiesByProductIds(@Param("productIds") List<String> productIds);


}
