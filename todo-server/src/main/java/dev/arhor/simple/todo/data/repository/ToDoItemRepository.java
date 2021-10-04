package dev.arhor.simple.todo.data.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import dev.arhor.simple.todo.data.model.ToDoItem;

@Repository
public interface ToDoItemRepository extends PagingAndSortingRepository<ToDoItem, Long> {

    List<ToDoItem> findAllByOwner(String owner);
}
