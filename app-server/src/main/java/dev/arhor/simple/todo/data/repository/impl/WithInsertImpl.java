package dev.arhor.simple.todo.data.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;

import dev.arhor.simple.todo.data.repository.WithInsert;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class WithInsertImpl<T> implements WithInsert<T> {

    private final JdbcAggregateTemplate jdbcAggregateTemplate;

    @Override
    public T insert(T entity) {
        return jdbcAggregateTemplate.insert(entity);
    }
}
