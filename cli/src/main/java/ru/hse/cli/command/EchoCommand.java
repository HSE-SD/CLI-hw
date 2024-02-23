package ru.hse.cli.command;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implements UNIX-like 'echo' command
 * <p>echo 1 2 www mem
 */
public class EchoCommand implements Command {

    @Override
    public Result invoke(List<String> args) {
        return new Result(
                Status.OK,
                Optional.of(
                        args.stream().skip(1).collect(Collectors.joining(" "))
                ),
                Optional.empty()
        );
    }
}
