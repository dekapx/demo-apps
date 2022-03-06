package net.dekapx.core.mapper;

public interface Mapper<Entity, Model> {
    Entity toEntity(Model model);

    Model toModel(Entity entity);

    void copyProperties(Entity source, Entity target);
}
