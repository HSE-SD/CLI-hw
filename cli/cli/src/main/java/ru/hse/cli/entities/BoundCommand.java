package ru.hse.cli.command;

import java.util.List;

public class BoundCommand {

    private final Command command;
    private final List<String> args;

    BoundCommand(Command command, List<String> args) {
        this.command = command;
        this.args = args;
    }

    Command.Result execute() {
        return command.invoke(args);
    }
}
