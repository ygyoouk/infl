package com.inf.orderservice.service;

import com.inf.orderservice.dto.OrderDto;
import com.inf.orderservice.jpa.OrderEntity;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDetails);
    OrderDto getOrderByOrderId(String orderId);
    Iterable<OrderEntity> getOrdersByUserId(String userId);
}
