package com.inventory.system.exotic0.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderLineDTO {

    private Long id;
    private Long variantId;
    private String variantName;
    private String variantBarcode;
    private Long productId;
    private String productName;
    private Long stockId;
    private Float unitPrice;
    private Long orderId;
    private int quantity;
    private Float originalAmount;
    private Boolean isGift;
    private Boolean isHasDiscount;
    private Float discountPercentage;
    private Float toPayAmount;
}
