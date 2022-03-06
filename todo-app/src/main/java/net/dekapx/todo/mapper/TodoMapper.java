package net.dekapx.todo.mapper;

import net.dekapx.core.mapper.Mapper;
import net.dekapx.todo.domain.Todo;
import net.dekapx.todo.model.TodoModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component("todoMapper")
public class TodoMapper implements Mapper<Todo, TodoModel> {
    @Override
    public Todo toEntity(final TodoModel model) {
        return new ModelMapper().map(model, Todo.class);
    }

    @Override
    public TodoModel toModel(final Todo todo) {
        return new ModelMapper().map(todo, TodoModel.class);
    }
}
