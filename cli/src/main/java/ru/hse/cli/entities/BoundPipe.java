package ru.hse.cli.entities;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import ru.hse.cli.command.Status;

public class BoundPipe {

	private final List<BoundCommand> commands;

	public BoundPipe(List<BoundCommand> commands) {
		this.commands = commands;
	}

	public Result execute() {
		InputStream in = System.in;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		for (var command : commands) {
			out = new ByteArrayOutputStream();
			command.setInputStream(in);
			command.setOutputStream(out);
			var result = command.execute();
			if (result.status() != Status.OK) {
				return result;
			}
			in = new ByteArrayInputStream(out.toByteArray());
		}
		try {
			System.out.write(out.toByteArray());
		} catch (IOException e) {
			return new Result(Status.ERROR, 1, Optional.of(e.getMessage()));
		}
		return new Result(Status.OK, 0, Optional.empty());
	}

}
