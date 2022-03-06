package net.dekapx.todo.service;

import net.dekapx.core.exception.NoContentException;
import net.dekapx.core.exception.ResourceNotFoundException;
import net.dekapx.todo.domain.Todo;
import net.dekapx.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.nonNull;

@Service
@Transactional
public class TodoServiceImpl implements TodoService {
    private TodoRepository todoRepository;

    @Autowired
    public TodoServiceImpl(final TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public Todo create(final Todo todo) {
        return this.todoRepository.save(todo);
    }

    @Override
    public Todo update(final Todo todo) {
        return this.todoRepository.save(todo);
    }

    @Override
    public void delete(final Long id) {
        checkArgument(nonNull(id), "Todo ID must not be null.");
        this.todoRepository.delete(findByIdFunction.apply(id));
    }

    @Override
    public Todo findById(final Long id) {
        checkArgument(nonNull(id), "Todo ID must not be null.");
        return findByIdFunction.apply(id);
    }

    private Function<Long, Todo> findByIdFunction = (id) -> {
        final var contactOptional = this.todoRepository.findById(id);
        return contactOptional.orElseThrow(
                () -> new ResourceNotFoundException(String.format("Todo with ID [%d] not found.", id)));
    };

    @Override
    public List<Todo> findAll() {
        final List<Todo> todos = new ArrayList<>();
        this.todoRepository.findAll().forEach(todo -> todos.add(todo));
        return Optional.of(todos)
                .filter(o -> (!o.isEmpty()))
                .orElseThrow(() -> new NoContentException("No contents found..."));
    }

    @Override
    public Todo findBySpecification(Specification<Todo> specification) {
        return null;
    }
}
