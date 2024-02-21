package ru.hse.cli.entities;

import ru.hse.cli.command.Command;

import java.util.List;

public class BoundCommand {

    private final Command command;
    private final List<String> args;

    public BoundCommand(Command command, List<String> args) {
        this.command = command;
        this.args = args;
    }

    public Command.Result execute() {
        return command.invoke(args);
    }
}
