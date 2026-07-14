package com.order_service.Order_service.repository;

import com.order_service.Order_service.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query(value = "select * from orders where order_number = :orderNumber", nativeQuery = true)
    public OrderEntity findByOrderNumber(@Param("orderNumber") String orderNumber);
}
