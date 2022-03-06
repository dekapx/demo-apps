package net.dekapx.todo.domain;

import lombok.Data;
import net.dekapx.core.domain.AbstractBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "todos")
public class Todo extends AbstractBaseEntity {
    @Column(name = "task", nullable = false)
    private String task;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "completed", nullable = false)
    private boolean completed;
}
