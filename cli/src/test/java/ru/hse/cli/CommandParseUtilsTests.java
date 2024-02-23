package ru.hse.cli;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ru.hse.cli.command.CatCommand;
import ru.hse.cli.command.Command;
import ru.hse.cli.command.EchoCommand;
import ru.hse.cli.command.ExternalCommand;
import ru.hse.cli.entities.BoundCommand;
import ru.hse.cli.utils.CommandParseUtils;

import java.util.List;

public class CommandParseUtilsTests {

    @Test
    public void SmokeTest() {
        String sampleCommandInput = "echo 'hello'";
        BoundCommand boundCommand = CommandParseUtils.parse(sampleCommandInput);
        List<String> args = boundCommand.getArgs();
        Command command = boundCommand.getCommand();
        Assertions.assertEquals(2, args.size());
        Assertions.assertEquals("echo 'hello'", args.get(0));
        Assertions.assertEquals("hello", args.get(1));
        Assertions.assertInstanceOf(EchoCommand.class, command);
    }

    @Test
    public void TrimWhitespacesTest() {
        String sampleCommandInput = "     cat       myfile.txt   ";
        BoundCommand boundCommand = CommandParseUtils.parse(sampleCommandInput);
        List<String> args = boundCommand.getArgs();
        Command command = boundCommand.getCommand();
        Assertions.assertEquals(2, args.size());
        Assertions.assertEquals("myfile.txt", args.get(1));
        Assertions.assertInstanceOf(CatCommand.class, command);
    }

    @Test
    public void FuzzerTest() {
        String sampleCommandInput = "khjskbvj vasfdjhvbj svkdsabb fnkjdv";
        BoundCommand boundCommand = CommandParseUtils.parse(sampleCommandInput);
        List<String> args = boundCommand.getArgs();
        Command command = boundCommand.getCommand();
        Assertions.assertEquals(4, args.size());
        Assertions.assertInstanceOf(ExternalCommand.class, command);
    }
}