package exercise.orders.repository;

import exercise.common.repositories.BaseRepository;
import exercise.orders.model.OrderEntity;

import java.util.List;

public interface OrderRepository extends BaseRepository<OrderEntity, Long> {

    List<OrderEntity> findByCustomerId(Long customerId);
}
