// Some code from the BasicBot in the Discord4J Documentation was used to get started
// Comment keywords: ERROR, SUGGESTION, WARNING

package randomImageBot;

import java.io.IOException;

import sx.blah.discord.api.IDiscordClient;

public class MainClass {
	
	static String BOT_TOKEN;
	
	public static void main(String[] args) throws IOException {
		
		if (args.length != 1) {
			System.out.println("Please enter the bots token as the first argument e.g java -jar thisjar.jar tokenhere");
			return;
		}
		
		IDiscordClient cli = BotUtils.getBuiltDiscordClient(args[0]);
		
		// Initialize BOT_TOKEN
		BOT_TOKEN = cli.getToken(); // WARNING: Starts with "BOT "
		
		// Register a listener via the EventSubscriber annotation which allows for organization and delegation of events
		cli.getDispatcher().registerListener(new MyEvents());
		
		// Only login after all events are registered otherwise some may be missed.
		cli.login();
	}
}
