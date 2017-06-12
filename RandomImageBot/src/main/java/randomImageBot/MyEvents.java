package randomImageBot;

import java.util.ArrayList;
import java.util.Arrays;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class MyEvents {
	
	@EventSubscriber
	public void onMessageReceived(MessageReceivedEvent event) {
		// Given a message "/test arg1 arg2", argArray will contain ["/test", "arg1", "arg"]
		String[] argArray = event.getMessage().getContent().split(" ");
		
		// First ensure at least the command and prefix is present, the arg length can be handled by your command function
		if(argArray.length == 0)
			return;
		
		// Check if the first arg (the command) starts with the prefix defined in the utils class
		if(!argArray[0].startsWith(BotUtils.BOT_PREFIX))
			return;
		
		// Extract the "command" part of the first arg out by just ditching the first character
		String commandStr = argArray[0].substring(1);
		
		// Load the rest of the args in the array into a List for safer access
		ArrayList<String> argsList = new ArrayList<>(Arrays.asList(argArray));
		argsList.remove(0); // Remove the command
		
		// Begin the switch to handle the string to command mappings. It's likely wise to pass the whole event or
		// some part (IChannel) to the command handling it
		switch (commandStr) {
			case "addFolder":
				BotUtils.addFolder(event.getChannel(), event.getAuthor().getStringID(), argsList);
				break;
			case "admin":
				BotUtils.addAdmin(event.getChannel(), event.getAuthor().getStringID(), argsList);
				break;
			// WARNING: "/pic" or "/pic " will act like /random. Intentional so users only need to use one core command rather than two
			case "pic":
				BotUtils.sendRandomFile(event.getChannel(), argsList);
				break;
			// Ensure /random has no arguments to be valid. Whitespace is fine though.
			case "random":
				if (argsList.size() == 0) {
					BotUtils.sendRandomFile(event.getChannel(), argsList);
					break;
				}
				else {
					BotUtils.sendMessage(event.getChannel(), "/random must have no arguments.");
					break;
				}
			// Logs out the client (bot). WARNING: Logs out the bot on all servers.
			case "sleep":
				BotUtils.sleep(event.getChannel(), event.getAuthor().getStringID());
				break;
			case "test":
				BotUtils.sendMessage(event.getChannel(), "I am sending a message from an EventSubscriber listener");
				break;
		}
	}
}