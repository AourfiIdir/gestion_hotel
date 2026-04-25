package com.app.models.DAO;
import java.util.Optional;
import java.util.List;
public interface DAO<T> {
    void insert(T t);
    void update(T t, String[]params);
    void delete(T t);

    Optional<T> get();
    List<T> getAll();
}
