package ru.hse.cli.command;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

import ru.hse.cli.entities.Result;

/**
 * Command without any effects
 */
public class EmptyCommand implements Command {

	@Override
	public Result invoke(List<String> args, InputStream in, OutputStream out) {
		return new Result(Status.OK, 0, Optional.empty());
	}
}
