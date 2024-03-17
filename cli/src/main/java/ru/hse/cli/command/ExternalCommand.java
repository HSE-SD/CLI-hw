package ru.hse.cli.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ru.hse.cli.entities.Result;

/**
 * Delegates command to the system executor
 */
public class ExternalCommand implements Command {

	@Override
	public Result invoke(List<String> args, InputStream in, OutputStream out) {
		var runtime = Runtime.getRuntime();
		try {
			var process = runtime.exec(args.get(0));
			int resultCode = process.waitFor();

			var result = new BufferedReader(new InputStreamReader(process.getInputStream())).lines()
					.collect(Collectors.joining(System.lineSeparator()));

			if (resultCode == 0) {
				out.write(result.getBytes());
				return new Result(Status.OK, 0, Optional.empty());
			} else {
				return new Result(Status.ERROR, resultCode, Optional.empty());
			}

		} catch (IOException | InterruptedException e) {
			return new Result(Status.ERROR, 1, Optional.of(e.getMessage()));
		}
	}
}
