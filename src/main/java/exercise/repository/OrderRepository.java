package exercise.repository;

import exercise.common.repositories.BaseRepository;
import exercise.model.OrderEntity;

import java.util.List;

public interface OrderRepository extends BaseRepository<OrderEntity, Long> {

    List<OrderEntity> findByCustomerId(Long customerId);
}
