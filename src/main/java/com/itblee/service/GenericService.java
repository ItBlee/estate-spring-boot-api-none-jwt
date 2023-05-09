package com.itblee.service;

import java.util.Optional;

public interface GenericService<T> {
    Optional<T> findOne(Long id);
    Long save(T t);
    void update(T t);
    void delete(Long id);
}
