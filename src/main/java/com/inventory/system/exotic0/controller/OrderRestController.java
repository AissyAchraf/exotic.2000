package com.inventory.system.exotic0.controller;

import com.inventory.system.exotic0.entity.Order;
import com.inventory.system.exotic0.repository.OrderRepository;
import com.inventory.system.exotic0.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderRestController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/getAll")
    public List<Order> getAll() {
        return orderService.findAllByDate(LocalDateTime.now());
    }
}
