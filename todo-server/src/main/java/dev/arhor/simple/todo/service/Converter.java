package dev.arhor.simple.todo.service;

import java.util.List;

public interface Converter<E, D> {

    E convertDtoToEntity(D item);

    D convertEntityToDto(E item);

    List<D> batchConvertEntityToDto(Iterable<E> items);

    List<D> batchConvertEntityToDto(List<E> items);

    List<E> batchConvertDtoToEntity(Iterable<D> items);

    List<E> batchConvertDtoToEntity(List<D> items);
}
