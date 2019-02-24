package exercise.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T, E extends Serializable> extends JpaRepository<T, E> {

    List<T> findAll();
    Optional<T> findByUuid(String uuid);
    List<T> findByCreatedAtBetween(Timestamp startDate, Timestamp finishDate);
    List<T> findByModifiedAtBetween(Timestamp startDate, Timestamp finishDate);
    List<T> findBySoftDelete(boolean softDelete);

}
