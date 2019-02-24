package exercise.common.services;


import exercise.common.entities.BaseEntity;
import exercise.common.exceptions.EntityNotFoundException;
import exercise.common.exceptions.ValidationException;
import exercise.common.repositories.BaseRepository;
import exercise.util.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public abstract class BaseService<T extends BaseRepository, E extends BaseEntity> {

    @Autowired
    protected T repository;

    @Autowired
    protected ConvertUtil convertUtils;

    private Class<E> clazz;

    public Class<E> clazz() {

        if (this.clazz == null) {

            Class<?> actualClass = this.getClass();

            ParameterizedType pt = (ParameterizedType) actualClass.getGenericSuperclass();

            this.clazz = (Class<E>) pt.getActualTypeArguments()[1];
        }

        return this.clazz;
    }

    public <K> K findById(long id, Class<K> clazz) throws Exception {

        Optional<E> optionalEntity = this.repository.findById(id);

        if (!optionalEntity.isPresent()) {
            throw new EntityNotFoundException(this.clazz(), "id", String.valueOf(id));
        }

        E entity = optionalEntity.get();
        K optionalDTO = convertUtils.convert(entity, clazz);

        return optionalDTO;
    }

    public <K> K findByUuid(String uuid, Class<K> clazz) throws Exception {

        Optional<E> optionalEntity = this.repository.findByUuid(uuid);

        if (!optionalEntity.isPresent()) {
            throw new EntityNotFoundException(this.clazz(), "uuid", uuid);
        }

        E entity = optionalEntity.get();
        K dto = convertUtils.convert(entity, clazz);

        return dto;
    }

    public <K> List<K> findAll(Class<K> clazz) {

        List<E> all = repository.findAll();

        List<K> list = convertUtils.convert(all, clazz);

        return list;
    }

    public <K, D> D create(K dto, Class<D> clazz) throws Exception {

        E entity = convertUtils.convert(dto, this.clazz());
        D outDTO = convertUtils.convert(repository.save(entity), clazz);

        return outDTO;
    }

    public <K, D> D update(long id, K dto, Class<D> clazz) throws Exception {

        Optional<E> optionalEntity = this.repository.findById(id);

        if (!optionalEntity.isPresent()) {
            throw new EntityNotFoundException(this.clazz(), "id", String.valueOf(id));
        }

        E entity = optionalEntity.get();

        convertUtils.map(dto, entity);
        repository.save(entity);

        D outDTO = convertUtils.convert(entity, clazz);

        return outDTO;
    }

    public <K> K delete(long id, Class<K> clazz) throws Exception {

        Optional<E> optionalEntity = this.repository.findById(id);

        if (!optionalEntity.isPresent()) {
            throw new ValidationException("Entity not found");
        }

        E entity = optionalEntity.get();
        entity.setSoftDelete(true);

        repository.save(entity);

        K dto = clazz != null ?
                convertUtils.convert(entity, clazz) : null;

        return dto;
    }

    public <K> K delete(String uuid, Class<K> clazz) throws Exception {

        Optional<E> optionalEntity = this.repository.findByUuid(uuid);

        if (!optionalEntity.isPresent()) {
            throw new ValidationException("Entity not found");
        }

        E entity = optionalEntity.get();
        entity.setSoftDelete(true);

        repository.save(entity);

        K dto = clazz != null ?
                convertUtils.convert(entity, clazz) : null;

        return dto;
    }

    public void delete(long id) throws Exception {
        this.delete(id, null);
    }

    public void delete(String uuid) throws Exception {
        this.delete(uuid, null);
    }

    public ConvertUtil getConvertUtils() {
        return convertUtils;
    }

}