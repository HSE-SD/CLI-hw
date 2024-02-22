package ru.hse.cli.command;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

/**
 * Implements UNIX-like 'pwd' command
 */
public class PwdCommand implements Command {

    @Override
    public Result invoke(List<String> args) {
        Path currentPath = Paths.get("");
        return new Result(
                Status.OK,
                Optional.of(currentPath.toAbsolutePath().toString()),
                Optional.empty()
        );
    }
}
