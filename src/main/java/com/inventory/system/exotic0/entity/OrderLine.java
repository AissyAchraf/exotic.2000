package com.inventory.system.exotic0.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "productVariantId", referencedColumnName = "id", insertable = false, updatable = false)
    private ProductVariant productVariant;
    private Float unitPrice;
    @ManyToOne
    @JoinColumn(name = "orderId", referencedColumnName = "id", insertable = false, updatable = false)
    private Order order;
    private int quantity;
    private Float originalAmount;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isGift = Boolean.FALSE;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isHasDiscount = Boolean.FALSE;
    private Float discountPercentage = 0F;
    private Float toPayAmount;
}
