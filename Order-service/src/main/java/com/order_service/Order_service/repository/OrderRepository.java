package com.order_service.Order_service.repository;

import com.order_service.Order_service.entity.OrderEntity;
import com.order_service.Order_service.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.AbstractQueue;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query(value = "select * from orders where order_number = :orderNumber", nativeQuery = true)
    public OrderEntity findByOrderNumber(@Param("orderNumber") String orderNumber);

    @Query(value = "UPDATE orders SET payment_status = :paymentStatus WHERE order_number = :orderNumber", nativeQuery = true)
    public OrderEntity findAndUpdateByOrderNumber(@Param("orderNumber")String orderNumber, @Param("paymentStatus") PaymentStatus paymentStatus);
}
