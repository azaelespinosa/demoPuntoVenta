package exercise.service;

import exercise.dto.OrderDto;

import java.util.List;

public interface OrderService {

    List<OrderDto> getAllOrdersForCustomer(Long customerId);
    OrderDto getOrderById(Long orderId);
    OrderDto createOrder(OrderDto dto);
    OrderDto updateOrder(OrderDto dto);

}
