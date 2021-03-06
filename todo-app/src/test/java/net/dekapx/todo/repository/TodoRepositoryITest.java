package net.dekapx.todo.repository;

import net.dekapx.todo.domain.Todo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TodoRepositoryITest {
    private static final String TASK = "Dummy Task";
    private static final String DESCRIPTION = "Dummy task for testing purpose";

    @Autowired
    private TodoRepository todoRepository;

    @Test
    @DisplayName("Todo CRUD Operations")
    public void createUpdateAndCleanupTodoTask() {
        create();
        verify(false);
        update();
        verify(true);
        cleanUp();
    }

    private void create() {
        final Todo todo = buildTodo.get();
        this.todoRepository.save(todo);
    }

    private void update() {
        final Todo todo = findByTask();
        todo.setCompleted(true);
        this.todoRepository.save(todo);
    }

    private void verify(boolean completed) {
        final Todo todo = findByTask();
        assertThat(todo).isNotNull()
                .satisfies(t -> {
                    assertThat(t.getTask()).isEqualTo(TASK);
                    assertThat(t.getDescription()).isEqualTo(DESCRIPTION);
                    assertThat(t.isCompleted()).isEqualTo(completed);
                });
    }

    private void cleanUp() {
        final Todo todo = findByTask();
        this.todoRepository.delete(todo);
    }

    private Supplier<Todo> buildTodo = () -> {
        final Todo todo = new Todo();
        todo.setTask(TASK);
        todo.setDescription(DESCRIPTION);
        todo.setCompleted(false);
        return todo;
    };

    private Todo findByTask() {
        return this.todoRepository.findByTask(TASK);
    }
}
