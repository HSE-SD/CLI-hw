package ru.hse.cli.command;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ru.hse.cli.entities.Result;

/**
 * Implements UNIX-like 'echo' command
 * <p>
 * echo 1 2 www mem
 */
public class EchoCommand implements Command {

	@Override
	public Result invoke(List<String> args, InputStream in, OutputStream out) {
		try {
			var res = args.stream().skip(1).map(String::trim).collect(Collectors.joining(" ")).getBytes();
			out.write(res);
			return new Result(Status.OK, 0, Optional.empty());
		} catch (IOException e) {
			return new Result(Status.ERROR, 1, Optional.of(e.getMessage()));
		}
	}
}
