package ua.dargunovskiy.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

public interface Dao<T, E> {
    void add(E entity);
    List<E> getAll();
    E update(E entity);
    void delete(T id);
}
