package dev.arhor.simple.todo.common;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public sealed interface Result<T> permits Result.Success, Result.Failure {

    static <T> Result<T> of(final Supplier<T> source) {
        try {
            return success(source.get());
        } catch (Exception e) {
            return failure(e);
        }
    }

    static <T> Success<T> success(final T value) {
        return new Success<>(value);
    }

    static <T> Failure<T> failure(final Throwable error) {
        return new Failure<>(error);
    }

    static <T> Failure<T> failure(final String message) {
        return new Failure<>(new IllegalStateException(message));
    }

    <R> Result<R> map(Function<? super T, ? extends R> mapper);

    <R> Result<R> flatMap(Function<? super T, ? extends Result<R>> mapper);

    void onSuccess(Consumer<? super T> action);

    void onFailure(Consumer<? super Throwable> action);

    record Success<T>(T value) implements Result<T> {

        @Override
        public <R> Result<R> map(final Function<? super T, ? extends R> mapper) {
            try {
                return success(mapper.apply(value));
            } catch (Exception e) {
                return failure(e);
            }
        }

        @Override
        public <R> Result<R> flatMap(final Function<? super T, ? extends Result<R>> mapper) {
            try {
                return mapper.apply(value);
            } catch (Exception e) {
                return failure(e);
            }
        }

        @Override
        public void onSuccess(final Consumer<? super T> action) {
            action.accept(value);
        }

        @Override
        public void onFailure(final Consumer<? super Throwable> action) {
            /* no-op by default */
        }
    }

    @SuppressWarnings("unchecked")
    record Failure<T>(Throwable error) implements Result<T> {

        @Override
        public <R> Result<R> map(final Function<? super T, ? extends R> mapper) {
            return (Result<R>) this;
        }

        @Override
        public <R> Result<R> flatMap(final Function<? super T, ? extends Result<R>> mapper) {
            return (Result<R>) this;
        }

        @Override
        public void onSuccess(final Consumer<? super T> action) {
            /* no-op by default */
        }

        @Override
        public void onFailure(final Consumer<? super Throwable> action) {
            action.accept(error);
        }
    }
}
