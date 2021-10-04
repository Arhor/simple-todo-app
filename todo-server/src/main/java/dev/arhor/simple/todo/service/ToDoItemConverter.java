package dev.arhor.simple.todo.service;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import dev.arhor.simple.todo.data.model.ToDoItem;

@Mapper(componentModel = "spring")
public interface ToDoItemConverter extends Converter<ToDoItem, ToDoItemDto> {

    @Override
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "dateTimeCreated", ignore = true)
    @Mapping(target = "dateTimeUpdated", ignore = true)
    ToDoItem convertDtoToEntity(ToDoItemDto item);

    @Override
    @InheritInverseConfiguration(name = "convertDtoToEntity")
    ToDoItemDto convertEntityToDto(ToDoItem item);
}
