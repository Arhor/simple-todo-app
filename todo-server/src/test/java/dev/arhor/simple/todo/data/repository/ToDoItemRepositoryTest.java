package dev.arhor.simple.todo.data.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.random.RandomGenerator;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import dev.arhor.simple.todo.data.model.ToDoItem;
import net.bytebuddy.utility.RandomString;

class ToDoItemRepositoryTest extends DatabaseIntegrationTest {

    @Autowired
    private ToDoItemRepository repository;

    @BeforeEach
    void setup() {
        var testData = Stream.generate(this::generateToDoItem).limit(100).toList();
        repository.saveAll(testData);
    }

    @Test
    @DisplayName("findAll should return all existing todo items")
    void findAll_allExistingToDoItems() {
        // when
        var allToDoItems = repository.findAll();

        // then
        assertThat(allToDoItems)
            .isNotNull()
            .isNotEmpty();
    }

    private ToDoItem generateToDoItem() {
        var randomGenerator = RandomGenerator.getDefault();
        var toDoItem = new ToDoItem();

        toDoItem.setName(RandomString.make());
        toDoItem.setOwner(RandomString.make());
        toDoItem.setComplete(randomGenerator.nextBoolean());
        toDoItem.setDueDate(Instant.now().plusSeconds(randomGenerator.nextLong(3600, 3600 * 10)));

        return toDoItem;
    }
}