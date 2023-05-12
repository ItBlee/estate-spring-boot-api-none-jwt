package com.itblee.repository;

import com.itblee.repository.entity.BaseEntity;

public interface GenericRepository<T extends BaseEntity, ID> {

    default ID save(T entity) {
        throw new UnsupportedOperationException();
    }

    default void update(T entity) {
        throw new UnsupportedOperationException();
    }

    default void delete(ID id) {
        throw new UnsupportedOperationException();
    }

}
