package net.dekapx.todo.delegate;

import net.dekapx.core.mapper.Mapper;
import net.dekapx.todo.domain.Todo;
import net.dekapx.todo.model.TodoModel;
import net.dekapx.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TodoDelegateImpl implements TodoDelegate {
    @Autowired
    private TodoService todoService;

    @Autowired
    @Qualifier("todoMapper")
    private Mapper<Todo, TodoModel> todoMapper;

    @Override
    public TodoModel findById(final Long id) {
        final Todo todo = this.todoService.findById(id);
        return this.todoMapper.toModel(todo);
    }

    @Override
    public List<TodoModel> findAll() {
        final List<Todo> todos = this.todoService.findAll();
        return todos.stream()
                .map(todo -> this.todoMapper.toModel(todo))
                .collect(Collectors.toList());
    }

    @Override
    public TodoModel create(final TodoModel todoModel) {
        final Todo todo = this.todoMapper.toEntity(todoModel);
        return this.todoMapper.toModel(this.todoService.create(todo));
    }

    @Override
    public TodoModel update(final Long id, final TodoModel todoModel) {
        final Todo source = this.todoMapper.toEntity(todoModel);
        final Todo target = this.todoService.findById(id);
        this.todoMapper.copyProperties(source, target);
        return this.todoMapper.toModel(this.todoService.update(target));
    }

    @Override
    public void delete(final Long id) {
        this.todoService.delete(id);
    }
}
