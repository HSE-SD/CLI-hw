package ru.hse.cli.entities;

import java.util.Optional;

import ru.hse.cli.command.Status;

/**
 * @param status  status of execution
 * @param value   return value
 * @param message error message
 */
public record Result(Status status, Integer value, Optional<String> message) {
};
