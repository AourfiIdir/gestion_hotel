package com.app.models.DAO;
import java.util.Optional;
import java.util.Vector;
public interface DAO<T> {
    void insert(T t);
    void update(T t, String[]params);
    void delete(T t);

    Optional<T> get(String s);
    Vector<T> getAll();
}
