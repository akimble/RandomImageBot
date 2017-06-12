package randomImageBot;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RequestBuffer;

class BotUtils {
	
	// Constants for use throughout the bot
	static String BOT_PREFIX = "/";
	// SUGGESTION: If bot goes down, these empty out. Maybe make backup as a text file
	static ArrayList<String> admins = new ArrayList<String>();
	static HashMap<String, String> hashMap = new HashMap<String, String>();
	
	// Adding default folder path for /random
	static {
		hashMap.put("", "C:\\Users\\Andrew\\Pictures\\Carl");
	}
	
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
	
	// Send an file from a folder to the channel
	static void sendFile(IChannel channel) {
		File pathToFile;
		
		pathToFile = new File("C:/Users/Andrew/Pictures/Fbt3pvR.png"); //Change file path HERE
		
		RequestBuffer.request(() -> {
			try {
				channel.sendFile(pathToFile);
			} catch (FileNotFoundException e) {
				System.err.println("File not found");
				e.printStackTrace();
			}
		});
	}
	
	// Return the file names inside a directory
	static File[] returnPathnames(IChannel channel, String fullListArgs) {
		File directoryPath;
		File[] paths;
		File[] filePathsArray;
		ArrayList<File> filePaths = new ArrayList<File>();;
		
		// Check if argsList is a key in namePath map object and then execute below code
		if (hashMap.containsKey(fullListArgs)) {
			directoryPath = new File(hashMap.get(fullListArgs));
			// Returns pathnames for files and directories
			paths = directoryPath.listFiles();
			// Filter out the directories
			for (File file : paths) {
				if (file.isFile()) 
					filePaths.add(file);
			}
		}
		else {
			paths = null;
			sendMessage(channel, "Please use valid keyword(s).");
		}
		
		filePathsArray = filePaths.toArray(new File[filePaths.size()]);
		
		return filePathsArray;
	}
	
	// Send a random file from a folder to channel
	static void sendRandomFile(IChannel channel, ArrayList<String> argsList) {
		File[] paths;
		Random rand = new Random();
		String fullListArgs;
		File randomFile;
		
		fullListArgs = String.join(" ", argsList); // Java 8 or later
		paths = returnPathnames(channel, fullListArgs);
		// If there are files in the folder (and it's an actual folder)...
		if (paths != null) {
			// ...choose a random file
			randomFile = paths[rand.nextInt(paths.length)];
//			// While randomFile is a directory, reroll for a file
//			while (randomFile.isDirectory()) {
//				randomFile = paths[rand.nextInt(paths.length)];
//			}
			
			RequestBuffer.request(() -> {
				try {
					channel.sendFile(randomFile);
				} catch (FileNotFoundException e) {
					System.err.println("File not found");
					e.printStackTrace();
				}
			});
		}
		else {
			sendMessage(channel, "Choose a folder at the end of the path name when using /addFolder.");
		}
		
		System.out.println("argsList:" + fullListArgs);
		System.out.println("hashMap:" + hashMap);
	}
	
	// Add user as a Bot Admin to use dev commands
	static void addAdmin(IChannel channel, String UserID, ArrayList<String> argsList) {
		String fullListToken;
		
		fullListToken = String.join(" ", argsList); // Java 8 or later
		
		System.out.println("UserID:" + UserID);
		System.out.println("Bot Token:" + MainClass.BOT_TOKEN);
		System.out.println("fullListToken:" + fullListToken);
		
		if (fullListToken.equals(MainClass.BOT_TOKEN)) { // Remember to use .equals to compare strings in Java
			if (admins.contains(UserID)) 
				sendMessage(channel, "You're already a Bot Admin.");
			else {
				admins.add(UserID);
				sendMessage(channel, "You are now a Bot Admin.");
			}
		}
		else {
			sendMessage(channel, "That's not my token.");
		}
	}
	
	// Add folder and folder path as options for /pic [FOLDER NAME] if user if a Bot Admin
	// SUGGESTION: If the folder doesn't exist it returns NullPointer. Check with Absolute Paths...
	// ...This will allow addFolder to call an error if the folder doesn't exist
	// WARNING: Multiple admins can play around on your PC by seeing what folders exist and uploading...
	// files from them. Be careful who you let become an admin (ideally it should be only you)!
	static void addFolder(IChannel channel, String UserID, ArrayList<String> argsList) {
		// If the user is a Bot Admin...
		if (admins.contains(UserID)) {
			String folderName;
			String folderPath;
			
			// ...and if there are only two arguments (folderName folderPath)...
			if (argsList.size() == 2) {
				folderName = argsList.get(0);
				argsList.remove(0); // Remove the folder name from argsList
				folderPath = String.join(" ", argsList);
				
				// ...and if the folderPath is valid (and not a file path)...
				if (Paths.get(folderPath) != null) {
					// ...add the folderName and folderPath to hashMap
					hashMap.put(folderName, folderPath); // WARNING: Adding the same key will replace the value
					sendMessage(channel, folderName + " - " + folderPath + " added.");
				}
				else {
					sendMessage(channel, folderPath + " is not a valid FOLDER path.");
				}
			}
			else {
				sendMessage(channel, "Too few or too many arguments.");
			}
		}
		else {
			sendMessage(channel, "Only Bot Admins can use this command.");
		}
	}
	
	// Logs out the client if the user is a Bot Admin
	static void sleep(IChannel channel, String UserID) {
		if (admins.contains(UserID)) {
			MainClass.cli.logout();
		}
		else {
			sendMessage(channel, "Only Bot Admins can use this command.");
		}
	}
}
