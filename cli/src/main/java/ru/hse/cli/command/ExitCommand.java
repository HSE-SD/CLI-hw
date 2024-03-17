package ru.hse.cli.command;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

import ru.hse.cli.entities.Result;

/**
 * Stops current CLI's process
 */
public class ExitCommand implements Command {

	@Override
	public Result invoke(List<String> args, InputStream in, OutputStream out) {
		return new Result(Status.EXIT, 0, Optional.empty());
	}
}
