package com.inf.orderservice.controller;

import com.inf.orderservice.dto.OrderDto;
import com.inf.orderservice.jpa.OrderEntity;
import com.inf.orderservice.service.OrderService;
import com.inf.orderservice.vo.RequestOrder;
import com.inf.orderservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order-service")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final Environment env;


    @GetMapping("/health_check")
    public String healthCheck() {
        return "Its working in order-serivce : " + env.getProperty("local.server.port");
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@RequestBody RequestOrder order
                                                    , @PathVariable("userId") String userId) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrderDto orderDto = mapper.map(order, OrderDto.class);
        orderDto.setUserId(userId);

        OrderDto createdOrder = orderService.createOrder(orderDto);

        return ResponseEntity.ok(mapper.map(createdOrder, ResponseOrder.class));
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrder(@PathVariable("userId") String userId){
        Iterable<OrderEntity> orderList = orderService.getOrdersByUserId(userId);

        List<ResponseOrder> result = new ArrayList<>();

        orderList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseOrder.class));
        });

        return ResponseEntity.ok(result);
    }
}
