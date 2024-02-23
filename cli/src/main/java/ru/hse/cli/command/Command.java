package ru.hse.cli.command;

import java.util.List;
import java.util.Optional;

/**
 * Provide CLI's command implementation
 */
public interface Command {

    /**
     * Execute command with given args
     * @param args list of command args
     * @return result of command execution
     */
    Result invoke(List<String> args);

    static Command of(String name) {
        return switch (name) {
            case "cat" -> new CatCommand();
            case "echo" -> new EchoCommand();
            case "exit" -> new ExitCommand();
            case "pwd" -> new PwdCommand();
            case "wc" -> new WcCommand();
            default -> new ExternalCommand();
        };
    }

    /**
     * @param status status of execution
     * @param value command output
     * @param message error message
     */
    record Result(
            Status status,
            Optional<String> value,
            Optional<String> message
    ) {
    }
}
