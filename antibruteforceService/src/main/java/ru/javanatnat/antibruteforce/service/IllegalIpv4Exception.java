package ru.javanatnat.antibruteforce.service;

public class IllegalIpv4Exception extends RuntimeException{
    public IllegalIpv4Exception(String message) {
        super(message);
    }
}
