package com.inventory.system.exotic0.mapper;

import com.inventory.system.exotic0.dto.OrderDTO;
import com.inventory.system.exotic0.entity.Order;

public interface OrderMapper {

    OrderDTO fromOrder(Order order);
}
