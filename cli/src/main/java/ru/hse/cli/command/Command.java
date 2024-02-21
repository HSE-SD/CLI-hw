package ru.hse.cli.command;

import java.util.List;
import java.util.Optional;

public interface Command {

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

    record Result(
            Status status,
            Optional<String> value,
            Optional<String> message
    ) {
    }
}
