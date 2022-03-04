package net.dekapx.todo.repository;

import net.dekapx.todo.domain.Todo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends CrudRepository<Todo, Long> {
    Todo findByTask(String task);
}
