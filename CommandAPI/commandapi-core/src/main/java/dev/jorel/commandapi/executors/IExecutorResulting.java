package dev.jorel.commandapi.executors;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.bukkit.command.CommandSender;

import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

import dev.jorel.commandapi.exceptions.WrapperCommandSyntaxException;

/**
 * The interface for resulting command executors
 * @param <T> the commandsender
 */
public interface IExecutorResulting<T extends CommandSender> extends IExecutorTyped {
	
	/**
	 * Executes the command executor with the provided command sender and the provided arguments.
	 * @param sender the command sender for this command
	 * @param args the arguments provided to this command
	 * @return the value returned by this command if the command succeeds, 0 if the command fails
	 * @throws WrapperCommandSyntaxException if an error occurs during the execution of this command
	 */
	@SuppressWarnings("unchecked")
	@Override
	default int executeWith(CommandSender sender, Object[] args) throws WrapperCommandSyntaxException {
		Method runMethod = Arrays.stream(this.getClass().getDeclaredMethods()).filter(m -> m.getName().equals("run")).findFirst().get();
		Class<?> type = runMethod.getParameterTypes()[0];
		if(type.isInstance(sender)) {
			return this.run((T) type.cast(sender), args);
		} else {
			throw new WrapperCommandSyntaxException(
				new SimpleCommandExceptionType(
					new LiteralMessage("You must be a " + type.getSimpleName().toLowerCase() + " to run this command")
				).create()
			);
		}
	}

	/**
	 * Executes the command.
	 * @param sender the command sender for this command
	 * @param args the arguments provided to this command
	 * @return the value returned by this command
	 * @throws WrapperCommandSyntaxException if an error occurs during the execution of this command
	 */
	int run(T sender, Object[] args) throws WrapperCommandSyntaxException;
	
}
