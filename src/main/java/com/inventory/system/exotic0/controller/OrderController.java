package com.inventory.system.exotic0.controller;

import com.inventory.system.exotic0.entity.Order;
import com.inventory.system.exotic0.entity.OrderStatus;
import com.inventory.system.exotic0.entity.OrderType;
import com.inventory.system.exotic0.entity.ProductVariant;
import com.inventory.system.exotic0.service.OrderService;
import com.inventory.system.exotic0.service.ProductVariantService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductVariantService productVariantService;

    @GetMapping("/orders")
    public String viewOrders(
            Model model
    ) {
        List<Order> orderList = orderService.findAll();
        model.addAttribute("orderList", orderList);
        return "Orders/search";
    }

    @PostMapping("/create-order")
    public String processCreateOrder(
            Model model,
            RedirectAttributes attributes,
            HttpServletRequest request
    ) {
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
        attributes.addFlashAttribute("orderNumMessage", "Une nouvelle commande a été créé avec le numéro : "+savedOrder.getOrderNum());
        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }

    @GetMapping("/order")
    public String showOrder(
            Model model,
            @RequestParam("orderId") Long orderId)
    {
        List<ProductVariant> variants = productVariantService.findAll();
        Order order = orderService.getById(orderId);
        model.addAttribute("order", order);
        model.addAttribute("variants", variants);
        return "Orders/showOrder";
    }
}
