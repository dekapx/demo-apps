package net.dekapx.todo.service;

import net.dekapx.core.exception.ResourceNotFoundException;
import net.dekapx.todo.domain.Todo;
import net.dekapx.todo.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;

@ExtendWith(MockitoExtension.class)
public class TodoServiceImplTest {
    @Mock
    private Todo todoMock;
    @Mock
    private TodoRepository todoRepositoryMock;

    @InjectMocks
    private TodoServiceImpl todoService;

    @Test
    public void givenIdShouldFindAndReturnTodo() {
        given(this.todoRepositoryMock.findById(anyLong())).willReturn(Optional.of(todoMock));
        final Todo todo = this.todoService.findById(1L);
        assertThat(todo).isNotNull()
                .satisfies(t -> {
                    assertThat(t).isSameAs(todoMock);
                });
        verify(this.todoRepositoryMock, times(1)).findById(anyLong());
    }

    @Test
    public void shouldThrowExceptionWhenIdIsNotFound() {
        given(this.todoRepositoryMock.findById(anyLong())).willReturn(Optional.empty());

        Throwable throwable = assertThrows(ResourceNotFoundException.class, () -> {
            this.todoService.findById(1L);
        });

        assertThat(throwable).isNotNull()
                .satisfies(t -> {
                    assertThat(t.getMessage()).isEqualTo("Todo with ID [1] not found.");
                });
    }

    @Test
    public void givenInputShouldSaveAndReturnTodo() {
        given(this.todoRepositoryMock.save(todoMock)).willReturn(todoMock);

        final Todo todo = this.todoService.create(todoMock);
        assertThat(todo).isNotNull()
                .satisfies(t -> {
                    assertThat(t).isSameAs(todoMock);
                });
        verify(this.todoRepositoryMock, times(1)).save(todoMock);
    }

    @Test
    public void givenInputShouldUpdateAndReturnTodo() {
        given(this.todoRepositoryMock.save(todoMock)).willReturn(todoMock);

        final Todo todo = this.todoService.update(todoMock);
        assertThat(todo).isNotNull()
                .satisfies(t -> {
                    assertThat(t).isSameAs(todoMock);
                });
        verify(this.todoRepositoryMock, times(1)).save(todoMock);
    }

    @Test
    public void givenIdShouldDeleteTodo() {
        given(this.todoRepositoryMock.findById(anyLong())).willReturn(Optional.of(todoMock));
        this.todoService.delete(1L);
        verify(this.todoRepositoryMock, times(1)).delete(todoMock);
    }
}
