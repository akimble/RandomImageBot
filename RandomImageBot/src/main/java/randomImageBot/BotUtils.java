package randomImageBot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
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
	private static String BOT_ADMINS_FILENAME = "admins.txt";
	private static String BOT_KEYWORDTOPATH_FILENAME = "keywordToPath.txt";
	static String BOT_PREFIX = "/";
	static ArrayList<String> admins = new ArrayList<String>();
	static HashMap<String, String> keywordToPath = new HashMap<String, String>();
	
	// Adding default folder path for /random
	// SUGGESTION: Make /setRandom method
	static {
		keywordToPath.put("", "C:\\Users\\Andrew\\Pictures\\Carl");
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
	
	// Send a file from a folder to the channel
	static void sendFile(IChannel channel, File randomFile) {
		RequestBuffer.request(() -> {
			try {
				channel.sendFile(randomFile);
			} catch (FileNotFoundException e) {
				System.err.println("File not found");
				e.printStackTrace();
			}
		});
	}
	
	// Return the pathnames for files inside a folder
	static File[] returnPathnames(IChannel channel, String fullListArgs) {
		File folderPath;
		File[] paths;
		File[] filePathsArray;
		ArrayList<File> filePaths = new ArrayList<File>();;
		
		// Check if argsList is a key in namePath map object and then execute below code
		if (keywordToPath.containsKey(fullListArgs)) {
			folderPath = new File(keywordToPath.get(fullListArgs));
			// Returns pathnames for files and folders
			paths = folderPath.listFiles();
			// Filter out the folders
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
			// ...choose a random file and send it to the "channel"
			randomFile = paths[rand.nextInt(paths.length)];
			sendFile(channel, randomFile);
		}
		else {
			sendMessage(channel, "Choose a folder at the end of the path name when using /addFolder.");
		}
		
		System.out.println("argsList:" + fullListArgs);
		System.out.println("keywordToPath:" + keywordToPath);
	}
	
	// Add user as a Bot Admin to use dev commands
	static void addAdmin(IChannel channel, String userID, ArrayList<String> argsList) {
		String fullListToken;
		
		fullListToken = String.join(" ", argsList); // Java 8 or later
		
		if (fullListToken.equals(MainClass.BOT_TOKEN)) { // Remember to use .equals to compare strings in Java
			if (admins.contains(userID)) 
				sendMessage(channel, "You're already a Bot Admin.");
			else {
				admins.add(userID);
				writeToFile(userID, BOT_ADMINS_FILENAME);
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
	// ERROR: adds Duplicates in writeToFile even if readFromFile was made
	// WARNING: Multiple admins can play around on your PC by seeing what folders exist and uploading...
	// files from them. Be careful who you let become an admin (ideally it should be only you)!
	static void addFolder(IChannel channel, String userID, ArrayList<String> argsList) {
		// If the user is a Bot Admin...
		if (admins.contains(userID)) {
			String folderName;
			String folderPath;
			String concatenatedHashMapEntry;
			
			// ...and if there are only two arguments (folderName folderPath)...
			if (argsList.size() == 2) {
				folderName = argsList.get(0);
				argsList.remove(0); // Remove the folder name from argsList
				folderPath = String.join(" ", argsList);
				
				// ...and if the folderPath is valid (and not a file path)...
				if (Paths.get(folderPath) != null) {
					// ...add the folderName and folderPath to keywordToPath
					keywordToPath.put(folderName, folderPath); // WARNING: Adding the same key will replace the value
					concatenatedHashMapEntry = folderName + " " + folderPath;
					writeToFile(concatenatedHashMapEntry, BOT_KEYWORDTOPATH_FILENAME);
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
	static void sleep(IChannel channel, String userID) {
		if (admins.contains(userID)) {
			MainClass.cli.logout();
		}
		else {
			sendMessage(channel, "Only Bot Admins can use this command.");
		}
	}
	
	// Write a string to a text file
	// ERROR: Remember to make the readFromFile method to keep it from adding duplicate entries by reading in everything once into
	// admins and keyworthToPath arrayLists
	private static void writeToFile(String writeThisString, String fileName) {
		try {
			// Using a FileWriter since there's only one write before I close it. BufferedWriter would cause too much overhead
			// Starting with .\\src\\... because apparently Eclipse makes the project directory the working directory
			FileWriter f1 = new FileWriter(".\\src\\main\\resources\\text files\\" + fileName, true);
			f1.write(writeThisString + "\n");
			f1.close();
		} catch (Exception e) {
			System.err.println("Message could not be sent with error: ");
			e.printStackTrace();
		}
	}
}
