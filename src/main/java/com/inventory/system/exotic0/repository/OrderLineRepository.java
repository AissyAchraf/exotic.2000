package com.inventory.system.exotic0.repository;

import com.inventory.system.exotic0.entity.Order;
import com.inventory.system.exotic0.entity.OrderLine;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {

    @Query("SELECT o FROM OrderLine o WHERE o.order = :order")
    List<OrderLine> findAllByOrder(@Param("order")Order order);
}
