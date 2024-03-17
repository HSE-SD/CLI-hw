package ru.hse.cli.command;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ru.hse.cli.entities.Result;

/**
 * Implements UNIX-like 'cat' command
 * <p>
 * cat FILENAME
 */
public class CatCommand implements Command {

	@Override
	public Result invoke(List<String> args, InputStream in, OutputStream out) {
		var path = Path.of(args.get(1));

		try (var lines = Files.lines(path)) {
			var result = lines.collect(Collectors.joining(System.lineSeparator()));

			out.write(result.getBytes());

			return new Result(Status.OK, 0, Optional.empty());
		} catch (IOException e) {
			return new Result(Status.ERROR, 1, Optional.of(e.getMessage()));
		}
	}
}
