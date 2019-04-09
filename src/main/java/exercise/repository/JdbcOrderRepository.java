package exercise.repository;

import exercise.dto.OrderDto;

import java.util.Optional;

public interface JdbcOrderRepository {

    Optional<OrderDto> findByOrderId(Long orderId);
}
