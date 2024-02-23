package ru.hse.cli.command;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implements UNIX-like 'cat' command
 * <p>cat FILENAME
 */
public class CatCommand implements Command {

    @Override
    public Result invoke(List<String> args) {
        var path = Path.of(args.get(1));

        try (var lines = Files.lines(path)) {
            var result = lines.collect(Collectors.joining(System.lineSeparator()));

            return new Result(
                    Status.OK,
                    Optional.of(result),
                    Optional.empty()
            );
        } catch (IOException e) {
            return new Result(
                    Status.ERROR,
                    Optional.empty(),
                    Optional.of(e.getMessage())
            );
        }
    }
}
