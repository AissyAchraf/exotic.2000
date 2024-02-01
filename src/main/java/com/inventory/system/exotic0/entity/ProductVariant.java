package com.inventory.system.exotic0.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    @Column(unique = true)
    private String barcode;

    @OneToMany(mappedBy = "productVariant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stock> stocks = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Image image;

    private String size;

    @Transient
    public boolean getIsInStock() {
        boolean inStock = false;
        if(stocks != null) {
            for (Stock stock : stocks) {
                if(stock.getQuantity() > 0) {
                    inStock = true;
                    break;
                }
            }
        }
        return inStock;
    }

    @Transient
    public Float getMinSellingPrice() {
        if (stocks != null && !stocks.isEmpty()) {
            float minSellingPrice = stocks.get(0).getSellingPrice();
            for (int i = 1; i < stocks.size(); i++) {
                if (minSellingPrice > stocks.get(i).getSellingPrice()) {
                    minSellingPrice = stocks.get(i).getSellingPrice();
                }
            }
            return minSellingPrice;
        }
        return null;
    }

    @Transient
    public int getTotalStockQuantity() {
        int totalQuantity = 0;
        if (stocks != null && !stocks.isEmpty()) {
            for (Stock stock : stocks) {
                totalQuantity += stock.getQuantity();
            }
        }
        return totalQuantity;
    }
}

