package ru.hse.cli.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.hse.cli.command.Status;
import ru.hse.cli.entities.BoundPipe;
import ru.hse.cli.entities.Result;

public class PipeParseUtilsTest {

    @Test
    public void testExecuteWithSingleCommand() {
        String pipeClause = "ls -l";
        BoundPipe boundPipe = PipeParseUtils.parse(pipeClause);
        Result result = boundPipe.execute();
        Assertions.assertEquals(Status.OK, result.status());
        Assertions.assertFalse(result.message().isPresent());
    }

    @Test
    public void testExecuteWithMultipleCommands() {
        String pipeClause = "echo \"You wouldn't read this\" | echo \"boobs\"";
        BoundPipe boundPipe = PipeParseUtils.parse(pipeClause);
        Result result = boundPipe.execute();
        Assertions.assertEquals(Status.OK, result.status());
        Assertions.assertFalse(result.message().isPresent());
    }

    @Test
    public void testExecuteWithEmptyInput() {
        String pipeClause = "";
        BoundPipe boundPipe = PipeParseUtils.parse(pipeClause);
        Result result = boundPipe.execute();
        Assertions.assertEquals(Status.OK, result.status());
        Assertions.assertTrue(result.message().isEmpty());
    }

    @Test
    public void testExecuteWithNullInput() {
        String pipeClause = null;
        BoundPipe boundPipe = PipeParseUtils.parse(pipeClause);
        Result result = boundPipe.execute();
        Assertions.assertEquals(Status.EXIT, result.status());
        Assertions.assertTrue(result.message().isEmpty());
    }

    @Test
    public void testExecuteWithError() {
        String pipeClause = "invalid command like you";
        BoundPipe boundPipe = PipeParseUtils.parse(pipeClause);
        Result result = boundPipe.execute();
        Assertions.assertEquals(Status.ERROR, result.status());
    }
}
