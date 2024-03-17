package ru.hse.cli.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import ru.hse.cli.entities.Result;

/**
 * Implements UNIX-like 'wc' command
 * <p>
 * wc FILENAME
 */
public class WcCommand implements Command {

	record wcResult(Long linesCount, Long wordsCount, Long bytesCount) {
	};

	private wcResult countWc(Stream<String> lines) {
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
		return new wcResult(linesCount, wordsCount.get(), bytesCount.get());
	}

	@Override
	public Result invoke(List<String> args, InputStream in, OutputStream out) {
		Stream<String> lines;

		try {
			if (args.size() > 1) {
				var path = Path.of(args.get(1));
				lines = Files.lines(path);
			} else {
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				lines = reader.lines();
			}
			var res = countWc(lines);
			out.write(String.format("\t%s\t%s\t%s", res.linesCount(), res.wordsCount(), res.bytesCount()).getBytes());
			return new Result(Status.OK, 0, Optional.empty());
		} catch (IOException e) {
			return new Result(Status.ERROR, 1, Optional.of(e.getMessage()));
		}
	}
}
