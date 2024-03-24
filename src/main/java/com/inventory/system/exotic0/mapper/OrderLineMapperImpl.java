package com.inventory.system.exotic0.mapper;

import com.inventory.system.exotic0.dto.OrderLineDTO;
import com.inventory.system.exotic0.entity.OrderLine;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapperImpl implements OrderLineMapper {
    @Override
    public OrderLineDTO fromOrderLine(OrderLine orderLine) {
        OrderLineDTO dto = new OrderLineDTO();
        dto.setId(orderLine.getId());
        dto.setVariantId(orderLine.getProductVariant().getId());
        dto.setVariantBarcode(orderLine.getProductVariant().getBarcode());
        dto.setVariantName(orderLine.getProductVariant().getVariant());
        dto.setProductId(orderLine.getProductVariant().getProduct().getId());
        dto.setProductName(orderLine.getProductVariant().getProduct().getName());
        dto.setStockId(orderLine.getStock().getId());
        dto.setUnitPrice(orderLine.getUnitPrice());
        dto.setOrderId(orderLine.getId());
        dto.setQuantity(orderLine.getQuantity());
        dto.setOriginalAmount(orderLine.getOriginalAmount());
        dto.setIsGift(orderLine.getIsGift());
        dto.setIsHasDiscount(orderLine.getIsHasDiscount());
        dto.setDiscountPercentage(orderLine.getDiscountPercentage());
        dto.setToPayAmount(orderLine.getToPayAmount());
        return dto;
    }
}
