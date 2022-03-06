package net.dekapx.todo.mapper;

import net.dekapx.core.mapper.Mapper;
import net.dekapx.todo.domain.Todo;
import net.dekapx.todo.model.TodoModel;
import org.springframework.stereotype.Component;

@Component("todoMapper")
public class TodoMapper implements Mapper<Todo, TodoModel> {
    @Override
    public Todo toEntity(final TodoModel model) {
        final Todo todo = new Todo();
        todo.setTask(model.getTask());
        todo.setDescription(model.getDescription());
        todo.setCompleted(model.isCompleted());
        return todo;
    }

    @Override
    public TodoModel toModel(final Todo todo) {
        return TodoModel.builder()
                .id(todo.getId())
                .task(todo.getTask())
                .description(todo.getDescription())
                .completed(todo.isCompleted())
                .lastModifiedDate(todo.getLastModifiedDate())
                .build();
    }

    @Override
    public void copyProperties(final Todo source, final Todo target) {
        target.setTask(source.getTask());
        target.setDescription(source.getDescription());
        target.setCompleted(source.isCompleted());
    }
}
