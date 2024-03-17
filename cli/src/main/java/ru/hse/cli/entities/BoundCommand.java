package ru.hse.cli.entities;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import ru.hse.cli.command.Command;

/**
 * Command that bound with its args
 */
public class BoundCommand {

	private final Command command;
	private final List<String> args;
	private InputStream in;
	private OutputStream out;

	public BoundCommand(Command command, List<String> args) {
		this.command = command;
		this.args = args;
		this.in = System.in;
		this.out = System.out;
	}

	public Command getCommand() {
		return command;
	}

	public List<String> getArgs() {
		return args;
	}

	public void setInputStream(InputStream in) {
		this.in = in;
	}

	public void setOutputStream(OutputStream out) {
		this.out = out;
	}

	public InputStream getInputStream() {
		return this.in;
	}

	public OutputStream getOutputStream() {
		return this.out;
	}

	/**
	 * Execute command with its args
	 * 
	 * @return result of command execution
	 */
	public Result execute() {
		return command.invoke(args, in, out);
	}
}
