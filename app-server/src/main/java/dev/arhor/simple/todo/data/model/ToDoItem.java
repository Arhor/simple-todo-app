package dev.arhor.simple.todo.data.model;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table(ToDoItem.TABLE)
public class ToDoItem implements DomainObject<Long> {

    public static final String TABLE = "todo_items";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_OWNER = "owner";
    public static final String COLUMN_DUE_DATE = "due_date";
    public static final String COLUMN_COMPLETE = "complete";
    public static final String COLUMN_DATE_TIME_CREATED = "date_time_created";
    public static final String COLUMN_DATE_TIME_UPDATED = "date_time_updated";

    @Id
    @Column(COLUMN_ID)
    private Long id;

    @Column(COLUMN_NAME)
    private String name;

    @Column(COLUMN_OWNER)
    private String owner;

    @Column(COLUMN_DUE_DATE)
    private Instant dueDate;

    @Column(COLUMN_COMPLETE)
    private Boolean complete;

    @CreatedDate
    @Column(COLUMN_DATE_TIME_CREATED)
    private Instant dateTimeCreated;

    @LastModifiedDate
    @Column(COLUMN_DATE_TIME_UPDATED)
    private Instant dateTimeUpdated;
}
