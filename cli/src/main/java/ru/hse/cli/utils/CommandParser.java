package ru.hse.cli.utils;

import ru.hse.cli.command.Command;
import ru.hse.cli.command.EmptyCommand;
import ru.hse.cli.entities.BoundCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class CommandParser {

    private CommandParser() {
    }

    static public BoundCommand parse(String commandLine) {
        var tokens = splitToTokens(commandLine);

        if (!tokens.isEmpty()) {
            var command = Command.of(tokens.get(0));
            tokens.set(0, commandLine);
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
