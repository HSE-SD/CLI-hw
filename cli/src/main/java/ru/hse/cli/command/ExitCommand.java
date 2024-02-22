package ru.hse.cli.command;

import java.util.List;
import java.util.Optional;

/**
 * Stops current CLI's process
 */
public class ExitCommand implements Command {

    @Override
    public Result invoke(List<String> args) {
        return new Result(Status.EXIT, Optional.empty(), Optional.empty());
    }
}
