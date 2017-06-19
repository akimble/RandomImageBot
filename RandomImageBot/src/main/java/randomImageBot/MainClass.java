//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Author: Andrew Kimble
// Last Edited: 6/18/2017
// Language: Java 1.8
// API: Discord4j (unofficial Discord API)
//
// Goal: Upload a random image from one of the folders on the hosts system, 
// specified through the use of a "Bot Admin" adding folders to randomly pull from.
//
// Details: A Discord bot coded in Java that uses the Discord4J unofficial API to listen for commands (in the form of messages /test, /random, etc.) and
// sends feedback. The feedback ranges from sending a message back, uploading images, or uploading files. Bot admins can be added using the bot's token with
// /admin [TOKEN] to have access to various commands (/sleep and /addFolder). Using /addFolder [KEYWORD] [PATH] a bot admin can add a keyword and folder
// that the bot will randomly upload images from when given the /pic [KEYWORD] command in the channel.
//
// Some code from the BasicBot in the Discord4J Documentation was used to get started
// Comment keywords: ERROR, SUGGESTION, WARNING
///////////////////////////////////////////////////////////////////////////////

package randomImageBot;

import java.io.IOException;

import sx.blah.discord.api.IDiscordClient;

public class MainClass {
	
	static IDiscordClient cli;
	static String BOT_TOKEN;
	
	public static void main(String[] args) throws IOException {
		
		if (args.length != 1) {
			System.out.println("Please enter the bots token as the first argument e.g java -jar thisjar.jar tokenhere");
			return;
		}
		
		cli = BotUtils.getBuiltDiscordClient(args[0]);
		
		// Initialize BOT_TOKEN
		BOT_TOKEN = cli.getToken(); // WARNING: Starts with "BOT "
		
		// Register a listener via the EventSubscriber annotation which allows for organization and delegation of events
		cli.getDispatcher().registerListener(new MyEvents());
		
		// Only login after all events are registered otherwise some may be missed.
		cli.login();
	}
}
