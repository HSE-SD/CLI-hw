package ru.hse.cli.utils;

import ru.hse.cli.command.Command;
import ru.hse.cli.command.EmptyCommand;
import ru.hse.cli.command.ExitCommand;
import ru.hse.cli.entities.BoundCommand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Utils for single command clause parsing
 */

public class CommandParseUtils {

    private CommandParseUtils() {
    }

    /**
     * Parse command clause to command name and args and create a specific command
     * @param commandClause considered as command with args
     * @return {@code BoundCommand} suitable command bound with its arguments
     */
    static public BoundCommand parse(String commandClause) {
        if (commandClause == null) {
            return new BoundCommand(new ExitCommand(), Collections.emptyList());
        }

        var tokens = splitToTokens(commandClause);

        if (!tokens.isEmpty()) {
            var command = Command.of(tokens.get(0));
            tokens.set(0, commandClause);
            return new BoundCommand(command, tokens);
        }

        return new BoundCommand(new EmptyCommand(), tokens);
    }

    static private List<String> splitToTokens(String commandLine) {
        var pattern = Pattern.compile("\"(.*?)\"|'(.*?)'|(\\S+)");
        var matcher = pattern.matcher(commandLine);
        var tokens = new ArrayList<String>();

        while (matcher.find()) {
            for (int i = 1; i <= 3; ++i) {
                Optional.ofNullable(matcher.group(i)).ifPresent(tokens::add);
            }
        }

        return tokens;
    }
}
