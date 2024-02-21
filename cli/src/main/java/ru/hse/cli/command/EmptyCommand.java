package ru.hse.cli.command;

import java.util.List;
import java.util.Optional;

public class EmptyCommand implements Command {

    @Override
    public Result invoke(List<String> args) {
        return new Result(Status.OK, Optional.empty(), Optional.empty());
    }
}
