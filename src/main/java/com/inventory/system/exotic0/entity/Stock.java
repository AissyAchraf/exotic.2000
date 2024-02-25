package com.inventory.system.exotic0.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Date purchaseDate;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Date expirationDate;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean hasExpirationDate = Boolean.FALSE;
    private int quantity;
    private Integer currentQuantity;
    private float weight;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean hasWeight = Boolean.FALSE;
    private float purchasePrice;
    private float sellingPrice;
    @ManyToOne
    @JsonIgnore
    private ProductVariant productVariant;
}
