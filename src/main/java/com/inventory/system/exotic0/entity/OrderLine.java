package com.inventory.system.exotic0.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderLine implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "productVariantId", referencedColumnName = "id")
    @JsonIgnore
    private ProductVariant productVariant;
    @ManyToOne
    @JoinColumn(name = "stockId", referencedColumnName = "id")
    private Stock stock;
    private Float unitPrice;
    @ManyToOne
    @JoinColumn(name = "orderId", referencedColumnName = "id")
    @JsonIgnore
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
