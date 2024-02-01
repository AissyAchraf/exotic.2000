package com.inventory.system.exotic0.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date purchaseDate;
    private Date expirementDate;
    private int quantity;
    private float weight;
    private float purchasePrice;
    private float sellingPrice;
    @ManyToOne
    @JsonIgnore
    private ProductVariant productVariant;
}
