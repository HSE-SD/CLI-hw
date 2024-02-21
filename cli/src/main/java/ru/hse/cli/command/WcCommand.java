package ru.hse.cli.command;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

public class WcCommand implements Command {

    @Override
    public Result invoke(List<String> args) {
        var path = Path.of(args.get(1));

        try (var lines = Files.lines(path)) {
            AtomicReference<Long> wordsCount = new AtomicReference<>(0L);
            AtomicReference<Long> bytesCount = new AtomicReference<>(0L);
            var pattern = Pattern.compile("\\S+");

            long linesCount = lines.peek(line -> {
                var matcher = pattern.matcher(line);
                while (matcher.find()) {
                    wordsCount.getAndSet(wordsCount.get() + 1);
                }

                bytesCount.updateAndGet(v -> v + line.length() + System.lineSeparator().length());
            }).count();

            return new Result(
                Status.OK,
                Optional.of(String.format("\t%s\t%s\t%s", linesCount, wordsCount, bytesCount)),
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
