package com.francesco_giuliante.infobasic.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDAO<T> {
    T save(T entity);
    T update(T entity, int id);
    void delete(int id);
    Optional<T> findById(int id);
    List<T> findAll();
}
