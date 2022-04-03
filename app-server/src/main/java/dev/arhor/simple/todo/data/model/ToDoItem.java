package dev.arhor.simple.todo.data.model;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table("todo_items")
public class ToDoItem implements DomainObject<Long> {
    @Id
    @Column("id")
    private Long id;

    @Column("name")
    private String name;

    @Column("owner")
    private String owner;

    @Column("due_date")
    private Instant dueDate;

    @Column("complete")
    private Boolean complete;

    @CreatedDate
    @Column("date_time_created")
    private Instant dateTimeCreated;

    @LastModifiedDate
    @Column("date_time_updated")
    private Instant dateTimeUpdated;
}
