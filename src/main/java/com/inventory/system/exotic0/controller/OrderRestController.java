package com.inventory.system.exotic0.controller;

import com.inventory.system.exotic0.dto.OrderDTO;
import com.inventory.system.exotic0.dto.OrderLineDTO;
import com.inventory.system.exotic0.entity.*;
import com.inventory.system.exotic0.mapper.OrderLineMapper;
import com.inventory.system.exotic0.mapper.OrderMapper;
import com.inventory.system.exotic0.repository.OrderRepository;
import com.inventory.system.exotic0.service.OrderLineService;
import com.inventory.system.exotic0.service.OrderService;
import com.inventory.system.exotic0.service.ProductVariantService;
import com.inventory.system.exotic0.service.StockService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path ="/api/orders")
public class OrderRestController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderLineService orderLineService;
    @Autowired
    private ProductVariantService productVariantService;
    @Autowired
    private StockService stockService;
    @Autowired
    private OrderLineMapper orderLineMapper;
    @Autowired
    private OrderMapper orderMapper;

//    @CrossOrigin("http://localhost:8081")
    @ResponseBody
    @GetMapping("/test")
    public String index() {
        return "Hello world!";
    }

//    @CrossOrigin("http://localhost:8081")
    @ResponseBody
    @PostMapping("/testPost")
    public String index2() {
        return "Hello world2!";
    }

//    @CrossOrigin("http://localhost:8081")
    @GetMapping("/getOrder/{orderId}")
    public OrderDTO getOrder(
            @PathVariable Long orderId
    ) {
        Order order = orderService.getById(orderId);
        return orderMapper.fromOrder(order);
    }

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

    @PostMapping("/create-order-line")
    public OrderLine processCreateOrderLine(
            @ModelAttribute("orderLine") OrderLine orderLine,
            @PathVariable(name = "orderId") Long orderId,
            @PathVariable(name = "variantId") Long variantId) {
        Order order = orderService.getById(orderId);
        ProductVariant productVariant = productVariantService.getById(variantId);
        if(order == null || productVariant == null) {
            return null;
        } else {
            orderLine.setOrder(order);
            orderLine.setProductVariant(productVariant);
            orderLine.setUnitPrice(productVariant.getAvailableStocks().get(0).getSellingPrice());
            orderLine.setOriginalAmount(orderLine.getQuantity()*orderLine.getUnitPrice());
            orderLine.setToPayAmount(orderLine.getOriginalAmount());
            return new OrderLine();
        }
    }

//    @CrossOrigin("*")
    @PostMapping("/createOrderLine")
    public OrderLine processCreateOrderLine(
            @RequestParam("barcode") String barcode,
            @RequestParam("orderId") Long orderId,
            @RequestParam("quantity") int quantity) {
        Order order = orderService.getById(orderId);
        ProductVariant productVariant = productVariantService.getByBarcode(barcode);
        if(order == null || productVariant == null) {
            return null;
        } else {
            OrderLine orderLine = new OrderLine();

            // sell from the old stock by default
            Stock stock = productVariant.getAvailableStocks().get(0);
            // update Stock Quantity
            stock.setCurrentQuantity(stock.getCurrentQuantity() - quantity);
            stock = stockService.save(stock);

            orderLine.setOrder(order);
            orderLine.setProductVariant(productVariant);
            orderLine.setQuantity(quantity);
            orderLine.setStock(stock);
            orderLine.setUnitPrice(stock.getSellingPrice());
            orderLine.setOriginalAmount(orderLine.getQuantity()*orderLine.getUnitPrice());
            orderLine.setToPayAmount(orderLine.getOriginalAmount());
            orderLineService.save(orderLine);

            // update order's total amount
            order.setTotalAmount(order.getTotalAmount()+orderLine.getToPayAmount());
            orderService.save(order);

            return orderLine;
        }
    }

    @GetMapping("/getOrderLines/{orderId}")
    public List<OrderLineDTO> getOrderLines(
            @PathVariable Long orderId
    ) {
        Order order = orderService.getById(orderId);
        if(order == null) {
            return null;
        }
        return orderLineService.findAllByOrder(order)
                .stream()
                .map(orderLine -> orderLineMapper.fromOrderLine(orderLine))
                .toList();
    }
}
