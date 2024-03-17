package ru.hse.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import ru.hse.cli.utils.PipeParseUtils;

/**
 * Application entry point
 */

public class CLI {

	public static void main(String[] args) throws IOException {
		var reader = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			System.out.print("cli$ ");
			try {
				var input = reader.readLine();
				var pipe = PipeParseUtils.parse(input);
				var result = pipe.execute();

				switch (result.status()) {
				case OK -> {
					System.out.println();
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
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}
}
