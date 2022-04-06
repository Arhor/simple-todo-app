package dev.arhor.simple.todo.data.repository;

public interface WithInsert<T> {

    T insert(T entity);
}
