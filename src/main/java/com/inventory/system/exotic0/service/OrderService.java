package com.inventory.system.exotic0.service;

import com.inventory.system.exotic0.entity.Order;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface OrderService {

    Order save(Order order);
    List<Order> findAll();
    List<Order> findAllByDate(LocalDateTime date);
    Order getById(Long id);
    void delete(Order order);
}
