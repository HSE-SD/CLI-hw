package ru.hse.cli.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import ru.hse.cli.command.ExitCommand;
import ru.hse.cli.entities.BoundCommand;
import ru.hse.cli.entities.BoundPipe;

public class PipeParseUtils {

	/**
	 * Parse command clause to command name and args and create a specific command
	 * 
	 * @param commandClause considered as command with args
	 * @return {@code BoundCommand} suitable command bound with its arguments
	 */
	static public BoundPipe parse(String pipeClause) {

		List<BoundCommand> commands = new ArrayList<BoundCommand>();

		if (pipeClause == null) {
			commands.add(new BoundCommand(new ExitCommand(), Collections.emptyList()));
			return new BoundPipe(commands);
		}

		var tokens = splitToTokens(pipeClause);

		for (var token : tokens) {
			if (!token.trim().equals("|")) {
				commands.add(CommandParseUtils.parse(token));
			}
		}

		return new BoundPipe(commands);

	}

	static private List<String> splitToTokens(String commandLine) {
		var pattern = Pattern.compile("([^|]+)|(\\|)");
		var matcher = pattern.matcher(commandLine);
		var tokens = new ArrayList<String>();

		while (matcher.find()) {
			for (int i = 1; i <= 2; ++i) {
				Optional.ofNullable(matcher.group(i)).ifPresent(tokens::add);
			}
		}

		return tokens;
	}

}
