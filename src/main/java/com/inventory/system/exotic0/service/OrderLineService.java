package com.inventory.system.exotic0.service;

import com.inventory.system.exotic0.entity.Order;
import com.inventory.system.exotic0.entity.OrderLine;

import java.util.List;

public interface OrderLineService {

    OrderLine save(OrderLine orderLine);
    OrderLine getById(Long id);
    List<OrderLine> findAllByOrder(Order order);
    void delete(OrderLine orderLine);
}
