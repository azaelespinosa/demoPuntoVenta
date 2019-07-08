package exercise.purchase.repositories;

import exercise.purchase.model.ConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestConfigRepository extends JpaRepository<ConfigEntity, Long> {

    @Override
    Optional<ConfigEntity> findById(Long aLong);
}
