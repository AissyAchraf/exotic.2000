package com.inventory.system.exotic0.service;

import com.inventory.system.exotic0.entity.Order;
import com.inventory.system.exotic0.entity.OrderLine;
import com.inventory.system.exotic0.repository.OrderLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderLineServiceImpl implements OrderLineService {

    @Autowired
    private OrderLineRepository orderLineRepository;

    @Override
    public OrderLine save(OrderLine orderLine) {
        return orderLineRepository.save(orderLine);
    }

    @Override
    public OrderLine getById(Long id) {
        return orderLineRepository.getById(id);
    }

    @Override
    public List<OrderLine> findAllByOrder(Order order) {
        return orderLineRepository.findAllByOrder(order);
    }

    @Override
    public void delete(OrderLine orderLine) {
        orderLineRepository.delete(orderLine);
    }
}
