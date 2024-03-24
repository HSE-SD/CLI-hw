package ru.hse.cli.command;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.hse.cli.entities.Result;

/**
 * Implement UNIX-like 'grep' command.
 */
public class GrepCommand implements Command {

    @Override
    public Result invoke(List<String> args, InputStream in, OutputStream out) {
        try {
            if (args.isEmpty()) {
                return new Result(Status.ERROR, 1, Optional.of("Usage: grep <pattern> [-w] [-i] [-A <num>] [file]"));
            }

            boolean ignoreCase = false;
            boolean wholeWord = false;
            int afterContext = 0;
            String filename = null;

            int i = 1;
            String target = null;
            label:
            while (i < args.size()) {
                String arg = args.get(i);
                switch (arg) {
                    case "-w":
                        wholeWord = true;
                        break;
                    case "-i":
                        ignoreCase = true;
                        break;
                    case "-A":
                        if (i + 1 < args.size()) {
                            try {
                                afterContext = Integer.parseInt(args.get(i + 1));
                                i++;
                            } catch (NumberFormatException e) {
                                return new Result(Status.ERROR, 1, Optional.of("Error: Invalid value for -A option"));
                            }
                        } else {
                            return new Result(Status.ERROR, 1, Optional.of("Error: -A option requires a numerical value"));
                        }
                        break;
                    default:
                        target = arg;
                        if (i + 1 < args.size()) {
                            filename = args.get(i + 1);
                        }
                        break label;
                }
                i++;
            }

            Pattern pattern = Pattern.compile(target);
            int flags = 0;
            if (ignoreCase) flags |= Pattern.CASE_INSENSITIVE;
            if (wholeWord) pattern = Pattern.compile("\\b" + target + "\\b", flags);

            print(pattern, filename != null ? new FileReader(filename) : new InputStreamReader(in), out, afterContext);

            return new Result(Status.OK, 0, Optional.empty());
        } catch (IOException e) {
            return new Result(Status.ERROR, 1, Optional.of("Error: " + e.getMessage()));
        }
    }

    private void print(Pattern pattern, InputStreamReader in, OutputStream out, int afterContext) throws IOException {
        BufferedReader reader = new BufferedReader(in);
        String line;
        List<String> text = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            text.add(line);
        }
        for (int i = 0; i < text.size(); ++i) {
            line = text.get(i);
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
                for (int j = i; j < text.size() && j - i <= afterContext; ++j) {
                    writer.write(text.get(j));
                    writer.newLine();
                }
                writer.flush();
            }
        }
        reader.close();
    }

}
