package net.dekapx.todo.adapter;

import net.dekapx.todo.model.TodoModel;

import java.util.List;

public interface TodoAdapter {
    TodoModel findById(Long id);

    List<TodoModel> findAll();

    TodoModel create(TodoModel todoModel);

    TodoModel update(Long id, TodoModel todoModel);

    void delete(Long id);
}
