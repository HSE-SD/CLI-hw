package ru.hse.cli;

import ru.hse.cli.utils.CommandParseUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Application entry point
 */

public class CLI {

    public static void main(String[] args) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.print("cli$ ");

            var input = reader.readLine();
            var command = CommandParseUtils.parse(input);
            var result = command.execute();

            switch (result.status()) {
                case OK -> {
                    if (result.value().isPresent()) {
                        System.out.println(result.value().get());
                    }
                }
                case ERROR -> {
                    if (result.message().isPresent()) {
                        System.err.println(result.message().get());
                    }
                }
                case EXIT -> {
                    return;
                }
            }
        }
    }
}
