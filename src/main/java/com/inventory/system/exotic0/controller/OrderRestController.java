package com.inventory.system.exotic0.controller;

import com.inventory.system.exotic0.entity.Order;
import com.inventory.system.exotic0.entity.OrderStatus;
import com.inventory.system.exotic0.entity.OrderType;
import com.inventory.system.exotic0.repository.OrderRepository;
import com.inventory.system.exotic0.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path ="/orders")
public class OrderRestController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/getAll")
    public List<Order> getAll() {
        return orderService.findAllByDate(LocalDateTime.now());
    }

    @PostMapping("/create-test")
    public ResponseEntity<Object> processCreateOrder() {
        List<Order> orderList = orderService.findAllByDate(LocalDateTime.now());
        Order order = new Order();
        if (!orderList.isEmpty()) {
            order.setOrderNum(orderList.size() + 1);
        } else {
            order.setOrderNum(1);
        }
        order.setStatus(OrderStatus.InProgress);
        order.setType(OrderType.Sale);
        order.setOrderDate(LocalDateTime.now());
        Order savedOrder = orderService.save(order);
        return ResponseEntity.ok(new Order());
    }
}
