package exercise.orders.jdbcrepository;

import exercise.orders.dto.OrderDto;

import java.util.Optional;

public interface JdbcOrderRepository {

    Optional<OrderDto> findByOrderId(Long orderId);

    Long findCustomerIdByOrderId(Long orderId);

    OrderDto findOrderByOrderId(Long orderId);
}
