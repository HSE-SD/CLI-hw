package ru.hse.cli.entities;

import ru.hse.cli.command.Command;

import java.util.List;

/**
 * Command that bound with its args
 */
public class BoundCommand {

    private final Command command;
    private final List<String> args;

    public BoundCommand(Command command, List<String> args) {
        this.command = command;
        this.args = args;
    }

    /**
     * Execute command with its args
     * @return result of command execution
     */
    public Command.Result execute() {
        return command.invoke(args);
    }
}
