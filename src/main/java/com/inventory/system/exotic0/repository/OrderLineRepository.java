package com.inventory.system.exotic0.repository;

import com.inventory.system.exotic0.entity.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
}
