package ru.hse.cli;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.hse.cli.command.Command;
import ru.hse.cli.command.ExternalCommand;
import ru.hse.cli.entities.BoundCommand;
import ru.hse.cli.utils.CommandParseUtils;

import static ru.hse.cli.command.Status.EXIT;
import static ru.hse.cli.command.Status.OK;

public class CommandTests {
    @Test
    public void EchoTest() {
        String sampleCommandInput = "echo 'hello\nworld'";
        BoundCommand boundCommand = CommandParseUtils.parse(sampleCommandInput);
        Command.Result result = boundCommand.execute();
        Assertions.assertEquals(OK, result.status());
        Assertions.assertEquals("'hello world'", result.value().get());
        Assertions.assertFalse(result.message().isPresent());
    }

    @Test
    public void PwdTest() {
        String sampleCommandInput = "pwd";
        BoundCommand boundCommand = CommandParseUtils.parse(sampleCommandInput);
        Command.Result result = boundCommand.execute();
        Assertions.assertEquals(OK, result.status());
        Assertions.assertTrue(result.value().isPresent());
        Assertions.assertFalse(result.message().isPresent());
    }

    @Test
    public void ExitTest() {
        String sampleCommandInput = "exit";
        BoundCommand boundCommand = CommandParseUtils.parse(sampleCommandInput);
        Command.Result result = boundCommand.execute();
        Assertions.assertEquals(EXIT, result.status());
        Assertions.assertFalse(result.value().isPresent());
        Assertions.assertFalse(result.message().isPresent());
    }

    @Test
    public void WcTest() {
        String sampleCommandInput = "wc ../cli/src/test/java/ru/hse/cli/test_data.txt";
        BoundCommand boundCommand = CommandParseUtils.parse(sampleCommandInput);
        Command.Result result = boundCommand.execute();
        Assertions.assertEquals(OK, result.status());
        Assertions.assertTrue(result.value().isPresent());
        Assertions.assertFalse(result.message().isPresent());
    }

    @Test
    public void CatTest() {
        String sampleCommandInput = "cat ../cli/src/test/java/ru/hse/cli/test_data.txt";
        BoundCommand boundCommand = CommandParseUtils.parse(sampleCommandInput);
        Command.Result result = boundCommand.execute();
        Assertions.assertEquals(OK, result.status());
        Assertions.assertEquals("I want to fit in.", result.value().get());
        Assertions.assertFalse(result.message().isPresent());
    }

    @Test
    public void ExternalCommandTest() {
        String sampleCommandInput = "ls";
        BoundCommand boundCommand = CommandParseUtils.parse(sampleCommandInput);
        Command.Result result = boundCommand.execute();
        Assertions.assertEquals(OK, result.status());
        Assertions.assertTrue(result.value().isPresent());
        Assertions.assertFalse(result.message().isPresent());
        Assertions.assertInstanceOf(ExternalCommand.class, boundCommand.getCommand());
    }
}
