package exercise.orders.service;

import exercise.orders.dto.OrderDto;

import java.util.List;

public interface OrderService {

    List<OrderDto> getAllOrdersForCustomer(Long customerId);
    OrderDto getOrderById(Long orderId);
    OrderDto createOrder(OrderDto dto);
    OrderDto updateOrder(OrderDto dto);
    OrderDto getJdbcOrderById(Long orderId);
    OrderDto getJdbcOrderByOrderId(Long orderId);
    Long findCustomerIdByOrderId(Long orderId);

}
