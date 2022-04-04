package dev.arhor.simple.todo.service;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import dev.arhor.simple.todo.data.model.ToDoItem;
import dev.arhor.simple.todo.service.dto.ToDoItemDto;

@Mapper(componentModel = "spring")
public interface ToDoItemConverter {

    @Mapping(target = "owner", source = "owner")
    @Mapping(target = "dateTimeCreated", ignore = true)
    @Mapping(target = "dateTimeUpdated", ignore = true)
    ToDoItem convertDtoToEntity(ToDoItemDto item, String owner);

    @InheritInverseConfiguration(name = "convertDtoToEntity")
    ToDoItemDto convertEntityToDto(ToDoItem item);

    List<ToDoItemDto> batchConvertEntityToDto(List<ToDoItem> items);
}
