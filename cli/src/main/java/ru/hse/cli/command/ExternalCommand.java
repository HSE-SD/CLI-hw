package ru.hse.cli.command;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ExternalCommand implements Command {

    @Override
    public Result invoke(List<String> args) {
        var runtime = Runtime.getRuntime();
        try {
            var process = runtime.exec(args.get(0));
            int resultCode = process.waitFor();
            return new Result(Status.OK, Optional.empty(), Optional.empty());
        } catch (IOException | InterruptedException e) {
            return new Result(
                    Status.ERROR,
                    Optional.empty(),
                    Optional.of(e.getMessage())
            );
        }
    }
}
