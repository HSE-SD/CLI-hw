package ru.hse.cli;

import static ru.hse.cli.command.Status.EXIT;
import static ru.hse.cli.command.Status.OK;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.hse.cli.entities.BoundPipe;
import ru.hse.cli.entities.Result;
import ru.hse.cli.utils.PipeParseUtils;

public class CommandTests {

	private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

	@BeforeEach
	public void setUp() {
		System.setOut(new PrintStream(outputStreamCaptor));
	}

	@Test
	public void EchoTest() {
		String sampleCommandInput = "echo 'hello\nworld'";
		BoundPipe boundPipe = PipeParseUtils.parse(sampleCommandInput);
		Result result = boundPipe.execute();
		Assertions.assertEquals(OK, result.status());
		Assertions.assertEquals("'hello world'", outputStreamCaptor.toString().trim());
		Assertions.assertFalse(result.message().isPresent());
	}

	@Test
	public void PwdTest() {
		String sampleCommandInput = "pwd";
		BoundPipe boundPipe = PipeParseUtils.parse(sampleCommandInput);
		Result result = boundPipe.execute();
		Assertions.assertEquals(OK, result.status());
		Assertions.assertEquals(0, result.value());
		Assertions.assertFalse(result.message().isPresent());
	}

	@Test
	public void ExitTest() {
		String sampleCommandInput = "exit";
		BoundPipe boundPipe = PipeParseUtils.parse(sampleCommandInput);
		Result result = boundPipe.execute();
		Assertions.assertEquals(EXIT, result.status());
		Assertions.assertEquals(0, result.value());
		Assertions.assertFalse(result.message().isPresent());
	}

	@Test
	public void WcTest() {
		String sampleCommandInput = "wc ../cli/src/test/java/ru/hse/cli/test_data.txt";
		BoundPipe boundPipe = PipeParseUtils.parse(sampleCommandInput);
		Result result = boundPipe.execute();
		Assertions.assertEquals(OK, result.status());
		Assertions.assertEquals(0, result.value());
		Assertions.assertFalse(result.message().isPresent());
	}

	@Test
	public void PipeWcTest() {
		String sampleCommandInput = "echo kek | wc";
		BoundPipe boundPipe = PipeParseUtils.parse(sampleCommandInput);
		Result result = boundPipe.execute();
		Assertions.assertEquals(OK, result.status());
		Assertions.assertEquals(0, result.value());
		Assertions.assertEquals("1	1	5", outputStreamCaptor.toString().trim());
		Assertions.assertFalse(result.message().isPresent());
	}

	@Test
	public void CatTest() {
		String sampleCommandInput = "cat ../cli/src/test/java/ru/hse/cli/test_data.txt";
		BoundPipe boundPipe = PipeParseUtils.parse(sampleCommandInput);
		Result result = boundPipe.execute();
		Assertions.assertEquals(OK, result.status());
		Assertions.assertEquals(0, result.value());
		Assertions.assertEquals("I want to fit in.", outputStreamCaptor.toString().trim());
		Assertions.assertFalse(result.message().isPresent());
	}

	@Test
	public void ExternalCommandTest() {
		String sampleCommandInput = "ls";
		BoundPipe boundPipe = PipeParseUtils.parse(sampleCommandInput);
		Result result = boundPipe.execute();
		Assertions.assertEquals(OK, result.status());
		Assertions.assertEquals(0, result.value());
		Assertions.assertFalse(result.message().isPresent());
	}

}
