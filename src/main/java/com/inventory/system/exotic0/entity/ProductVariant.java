package com.inventory.system.exotic0.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductVariant implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    @Column(unique = true)
    private String barcode;

    @OneToMany(mappedBy = "productVariant", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Stock> stocks = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private Image image;

    private String variant;

    @Transient
    public boolean getIsInStock() {
        boolean inStock = false;
        if(stocks != null) {
            for (Stock stock : stocks) {
                if(stock.getCurrentQuantity() > 0) {
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
                totalQuantity += stock.getCurrentQuantity();
            }
        }
        return totalQuantity;
    }

    @Transient
    public List<Stock> getAvailableStocks() {
        List<Stock> availableStocks = new ArrayList<>();
        for (Stock stock : this.getStocks()) {
            if(stock.getCurrentQuantity() > 0) {
                availableStocks.add(stock);
            }
        }
        return availableStocks;
    }
}

