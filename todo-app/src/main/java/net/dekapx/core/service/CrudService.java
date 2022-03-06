package net.dekapx.core.service;

import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface CrudService<T> {
    T create(T entity);

    T update(T entity);

    void delete(Long id);

    T findById(Long id);

    List<T> findAll();

    T findBySpecification(Specification<T> specification);
}
