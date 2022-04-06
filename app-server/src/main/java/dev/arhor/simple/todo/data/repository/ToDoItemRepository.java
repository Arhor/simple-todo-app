package dev.arhor.simple.todo.data.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import dev.arhor.simple.todo.data.model.ToDoItem;

@Repository
public interface ToDoItemRepository extends PagingAndSortingRepository<ToDoItem, Long> {

    List<ToDoItem> findAllByOwner(String owner);

    @Modifying
    @Query("DELETE FROM " + ToDoItem.TABLE + " WHERE " + ToDoItem.COLUMN_DUE_DATE + " < :deadline")
    int deleteToDoItemsByDueDateBefore(Instant deadline);
}
