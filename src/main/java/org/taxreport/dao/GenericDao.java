package org.taxreport.dao;

import org.taxreport.entity.Entity;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T extends Entity> {
    void create(T t);

    Optional<T> getById(Long id);

    List<T> getAll();

    void update(T t);

    void delete(T t);
}
