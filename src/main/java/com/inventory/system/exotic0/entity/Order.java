package com.inventory.system.exotic0.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime orderDate;
    private int orderNum;
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.InProgress;
    private Float totalAmount;
    @Enumerated(EnumType.STRING)
    private OrderType type = OrderType.Sale;
}
