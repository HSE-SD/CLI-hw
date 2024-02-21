package ru.hse.cli.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ExternalCommand implements Command {

    @Override
    public Result invoke(List<String> args) {
        var runtime = Runtime.getRuntime();
        try {
            var process = runtime.exec(args.get(0));
            int resultCode = process.waitFor();

            var result = new BufferedReader(new InputStreamReader(process.getInputStream())).lines()
                    .collect(Collectors.joining(System.lineSeparator()));

            return new Result(Status.OK, Optional.of(result), Optional.empty());
        } catch (IOException | InterruptedException e) {
            return new Result(Status.ERROR, Optional.empty(), Optional.of(e.getMessage()));
        }
    }
}
