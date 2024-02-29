package com.inventory.system.exotic0.service;

import com.inventory.system.exotic0.entity.Order;
import com.inventory.system.exotic0.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> findAllByDate(LocalDateTime date) {
        return orderRepository.findAllOrdersByDate(date);
    }

    @Override
    public Order getById(Long id) {
        return orderRepository.getById(id);
    }

    @Override
    public void delete(Order order) {
        orderRepository.delete(order);
    }
}
