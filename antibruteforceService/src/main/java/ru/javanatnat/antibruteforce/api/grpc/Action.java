package ru.javanatnat.antibruteforce.api.grpc;

@FunctionalInterface
public interface Action {
    void execute();
}
