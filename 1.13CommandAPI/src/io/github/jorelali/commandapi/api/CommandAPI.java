package io.github.jorelali.commandapi.api;

import java.util.LinkedHashMap;

/**
 * Class to register commands with the 1.13 command UI
 *
 */
public class CommandAPI {

	private SemiReflector reflector;

	/**
	 * Creates a new instance of the CommandAPI (This tends to be rather
	 * performance heavy as of version 1.0)
	 */
	public CommandAPI() {
		try {
			this.reflector = new SemiReflector();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Register a new command
	 * 
	 * @param commandName
	 *            The name of the command to register (e.g. "god"). A forward
	 *            slash is not needed
	 * @param args
	 *            The mapping of argument descriptions to argument types, in the
	 *            order of execution.
	 * @param executor
	 *            The code to run when this command is performed
	 */
	public void register(String commandName, final LinkedHashMap<String, ArgumentType_OLD> args, CommandExecutor executor) {

		try {
			reflector.register(commandName, args, executor);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}