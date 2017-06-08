package discordapitest;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RequestBuffer;

class BotUtils {
	
	// Constants for use throughout the bot
	static String BOT_PREFIX = "/";
	// SUGGESTION: If bot goes down, this empties out. Maybe make backup as a text file
	private static ArrayList<String> admins = new ArrayList<String>();
	
	// Handles the creation and getting of a IDiscordClient object for a token
	static IDiscordClient getBuiltDiscordClient(String token) {
		// The ClientBuilder object is where you will attach your params for configuring the instance of your bot.
		// Such as withToken, setDaemon etc
		return new ClientBuilder().withToken(token).build();
	}
	
	// Send a message to channel
	static void sendMessage(IChannel channel, String message) {
		RequestBuffer.request(() -> {
			try {
				channel.sendMessage(message);
			} catch (DiscordException e) {
				System.err.println("Message could not be sent with error: ");
				e.printStackTrace();
			}
		});
		
	}
	
	// Send an image from a folder to channel
	static void sendImage(IChannel channel) {
		File pathToFile;
		
		pathToFile = new File("C:/Users/Andrew/Pictures/Fbt3pvR.png"); //Change image path HERE
		
		RequestBuffer.request(() -> {
			try {
				channel.sendFile(pathToFile);
			} catch (FileNotFoundException e) {
				System.err.println("File not found");
				e.printStackTrace();
			}
		});
	}
	
	// Send a random image from a folder to channel
	static void sendRandomImage(IChannel channel) {
		File directoryPath;
		File randomImage;
		File[] paths;
		Random rand = new Random();
		
		directoryPath = new File("C:/Users/Andrew/Pictures/Eggplant"); //Change directory HERE
		paths = directoryPath.listFiles(); // Returns pathnames for files and directories
		randomImage = paths[rand.nextInt(paths.length)];
		
		RequestBuffer.request(() -> {
			try {
				channel.sendFile(randomImage);
			} catch (FileNotFoundException e) {
				System.err.println("File not found");
				e.printStackTrace();
			}
		});
	}
	
	// Add user as a Bot Admin to use dev commands
	static void addAdmin(IChannel channel, String UserID, ArrayList<String> argsList) {
		String fullListToken;
		
		fullListToken = String.join(" ", argsList); // Java 8 or later
		
		System.out.println("UserID:"+UserID);
		System.out.println("Bot Token:"+MainClass.BOT_TOKEN);
		System.out.println("fullListToken:"+fullListToken);
		
		if (fullListToken.equals(MainClass.BOT_TOKEN)) { // Remember to use .equals to compare strings in Java
			if (admins.contains(UserID)) 
				sendMessage(channel, "You're already a Bot Admin.");
			else {
				admins.add(UserID);
				sendMessage(channel, "You are now a Bot Admin.");
			}
		}
		else {
			sendMessage(channel, "That's not my token!");
		}
	}
}
