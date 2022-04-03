package dev.arhor.simple.todo.data.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.time.Instant;
import java.util.random.RandomGenerator;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import dev.arhor.simple.todo.data.model.ToDoItem;
import dev.arhor.simple.todo.service.TimeService;
import net.bytebuddy.utility.RandomString;

class ToDoItemRepositoryTest extends DatabaseIntegrationTest {

    @Autowired
    private ToDoItemRepository repository;

    @Autowired
    private TimeService timeService;

    @BeforeEach
    void setUp() {
        var testData = Stream.generate(this::generateToDoItem).limit(100).toList();
        repository.saveAll(testData);
    }

    @Test
    @DisplayName("findAllByOwner should return all todo items with specific owner")
    void findAllByOwner_ownerId_allCorrespondingToDoItems() {
        // given
        var initialToDoItem = repository.save(generateToDoItem());
        var initialToDoItemOwner = initialToDoItem.getOwner();

        // when
        var toDoItems = repository.findAllByOwner(initialToDoItemOwner);

        // then
        assertThat(toDoItems)
            .isNotNull()
            .isNotEmpty()
            .contains(initialToDoItem);

        for (ToDoItem toDoItem : toDoItems) {
            var actualOwner = toDoItem.getOwner();
            assertThat(actualOwner)
                .isEqualTo(initialToDoItemOwner);
        }
    }

    @Test
    @DisplayName("deleteToDoItemsByDueDateBefore should remove todo items with dueDate before specified deadline")
    void deleteToDoItemsByDueDateBefore() {
        // given
        var dateWeekAfterNow = timeService.now().plus(Duration.ofDays(7));

        // when
        var numberOfToDoItemsBefore = repository.count();
        var removedToDoItems = repository.deleteToDoItemsByDueDateBefore(dateWeekAfterNow);
        var numberOfToDoItemsAfter = repository.count();

        // then
        assertThat(removedToDoItems)
            .isEqualTo(numberOfToDoItemsBefore);
        assertThat(numberOfToDoItemsAfter)
            .isZero();
    }

    private ToDoItem generateToDoItem() {
        var randomGenerator = RandomGenerator.getDefault();
        var toDoItem = new ToDoItem();

        toDoItem.setName(RandomString.make());
        toDoItem.setOwner(RandomString.make());
        toDoItem.setComplete(randomGenerator.nextBoolean());
        toDoItem.setDueDate(timeService.now().plusSeconds(randomGenerator.nextLong(3600, 3600 * 10)));

        return toDoItem;
    }
}