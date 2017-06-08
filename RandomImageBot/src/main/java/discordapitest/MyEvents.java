package discordapitest;

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
        switch (commandStr) { // ERROR: Actually do need each case to call a private method with its respective code

            case "test":
            	BotUtils.sendMessage(event.getChannel(), "I am sending a message from an EventSubscriber listener");
                break;
            case "image":
            	BotUtils.sendImage(event.getChannel());
            	break;
            case "random":
            	BotUtils.sendRandomImage(event.getChannel());
            	break;
            case "admin":
            	BotUtils.addAdmin(event.getChannel(), event.getAuthor().getStringID(), argsList);
            	break;

        }
		
//		if (event.getMessage().getContent().startsWith(BotUtils.BOT_PREFIX + "test"))
//			BotUtils.sendMessage(event.getChannel(), "I am sending a message from an EventSubscriber listener");
//		if (event.getMessage().getContent().startsWith(BotUtils.BOT_PREFIX + "image"))
//			BotUtils.sendImage(event.getChannel());
//		if (event.getMessage().getContent().startsWith(BotUtils.BOT_PREFIX + "random"))
//			BotUtils.sendRandomImage(event.getChannel());
//		if (event.getMessage().getContent().startsWith(BotUtils.BOT_PREFIX + "admin"))
//			BotUtils.addAdmin(event.getChannel(), event.getAuthor().getStringID(), argsList);
	}
}