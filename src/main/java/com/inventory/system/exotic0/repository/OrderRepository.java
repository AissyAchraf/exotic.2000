package com.inventory.system.exotic0.repository;

import com.inventory.system.exotic0.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Transactional
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE DATE(o.orderDate) = DATE(:date)")
    List<Order> findAllOrdersByDate(@Param("date") LocalDateTime date);
}
