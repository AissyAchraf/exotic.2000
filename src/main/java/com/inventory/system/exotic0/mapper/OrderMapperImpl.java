package com.inventory.system.exotic0.mapper;

import com.inventory.system.exotic0.dto.OrderDTO;
import com.inventory.system.exotic0.entity.Order;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class OrderMapperImpl implements OrderMapper {
    @Override
    public OrderDTO fromOrder(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(order, orderDTO);
        return orderDTO;
    }
}
