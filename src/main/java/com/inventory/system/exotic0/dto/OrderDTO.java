package com.inventory.system.exotic0.dto;

import com.inventory.system.exotic0.entity.OrderStatus;
import com.inventory.system.exotic0.entity.OrderType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDTO {

    private Long id;
    private LocalDateTime orderDate;
    private int orderNum;
    private OrderStatus status;
    private Float totalAmount;
    private OrderType type;
}
