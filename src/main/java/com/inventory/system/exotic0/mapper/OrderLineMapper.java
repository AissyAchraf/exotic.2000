package com.inventory.system.exotic0.mapper;

import com.inventory.system.exotic0.dto.OrderLineDTO;
import com.inventory.system.exotic0.entity.OrderLine;

public interface OrderLineMapper {

    OrderLineDTO fromOrderLine(OrderLine orderLine);
}
