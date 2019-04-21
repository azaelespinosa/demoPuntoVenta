package exercise.orders.repository;

import exercise.common.repositories.BaseRepository;
import exercise.orders.model.ItemEntity;

/**
 * Interface para el uso de JpaRepository.
 * @author: Azael Espinosa
 * @version: 12/10/2018/V1.0
 */

public interface ItemRepository extends BaseRepository<ItemEntity, Long> {
}
