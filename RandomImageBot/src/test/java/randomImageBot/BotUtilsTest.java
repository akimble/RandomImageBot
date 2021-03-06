// The changes to storing admin userIDs and the keywordToPath hashmap in text files have broken these tests.
// Honestly this testing is making programming this bot less fun and insightful because I have to spend
// more time writing tests instead of working on new functionality. I feel like any time I save with these
// particular unit tests doesn't make up for the time I spend writing them. This program just seems too 
// small to really benefit greatly from them.

// Will stay BORKED until I find a good reason to add back the testing.

package randomImageBot;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

public class BotUtilsTest {

	@Test
	public void testAddAdmin() {
		ArrayList<String> argsList = new ArrayList<String>();
		ArrayList<String> testAdmins = new ArrayList<String>();
		
		// Test for "uniqueTokenID" and "uniqueUserID01"
		MainClass.BOT_TOKEN = "uniqueTokenID";
		argsList.add("uniqueTokenID");
		BotUtils.addAdmin(null, "uniqueUserID01", argsList);
		testAdmins.add("uniqueUserID01");
		
		assertEquals(testAdmins, BotUtils.admins);
		
		// Test for "uniqueTokenID" and the userID is empty
		BotUtils.addAdmin(null, "", argsList);
		testAdmins.add("");
		
		assertEquals(testAdmins, BotUtils.admins);
		
		// Test for entering the same userID. Nothing should be added
		BotUtils.addAdmin(null, "uniqueUserID01", argsList);
		
		assertEquals(testAdmins, BotUtils.admins);
		
		// Test for entering the same empty userID. Nothing should be added
		BotUtils.addAdmin(null, "", argsList);
		
		assertEquals(testAdmins, BotUtils.admins);
		
		// Test for adding null as the userID
		BotUtils.addAdmin(null, null, argsList);
		testAdmins.add(null);
		
		assertEquals(testAdmins, BotUtils.admins);
	}

	@Test
	public void testAddFolder() {
		ArrayList<String> argsList = new ArrayList<String>();
		HashMap<String, String> testHashMap = new HashMap<String, String>();
		String arg0;
		String arg1;
		String arg2;
		String arg3;
		String arg4;
		String arg5;
		
		// Initialize testHashMap because of the static block in BotUtils
		testHashMap.put("", "C:\\Users\\Andrew\\Pictures\\Carl");
		// User must be a Bot Admin to user /addFolder
		BotUtils.admins.add("uniqueUserID");
		arg0 = "folderName01";
		arg1 = "C:\\pictures\\folder1";
		arg2 = "folderName02";
		arg3 = "C:/pictures/folder2";
		arg4 = "C:\\pictures/folder2";
		arg5 = "C:\\pictures\\new folder";
		
		// Test for "\\" in folder path (second argument)
		argsList.add(arg0);
		argsList.add(arg1);
		BotUtils.addFolder(null, "uniqueUserID", argsList);
		testHashMap.put(arg0, arg1);
		
		assertEquals(testHashMap, BotUtils.keywordToPath);
		
		// Test for only "/" in folder path
		argsList.clear();
		argsList.add(arg2);
		argsList.add(arg3);
		BotUtils.addFolder(null, "uniqueUserID", argsList);
		testHashMap.put(arg2, arg3);
		
		assertEquals(testHashMap, BotUtils.keywordToPath);
		
		// Test for "\\" and "/" in folder path
		argsList.clear();
		argsList.add(arg2);
		argsList.add(arg4);
		BotUtils.addFolder(null, "uniqueUserID", argsList);
		testHashMap.put(arg2, arg4);
		
		assertEquals(testHashMap, BotUtils.keywordToPath);
		
		// Test for spaces in the folder path
		argsList.clear();
		argsList.add(arg2);
		argsList.add(arg5);
		BotUtils.addFolder(null, "uniqueUserID", argsList);
		testHashMap.put(arg2, arg5);
		
		assertEquals(testHashMap, BotUtils.keywordToPath);
		
		// Test with no arguments
		argsList.clear();
		BotUtils.addFolder(null, "uniqueUserID", argsList);
		
		assertEquals(testHashMap, BotUtils.keywordToPath);
		
		// Test with more than 2 arguments
		argsList.add(arg0);
		argsList.add(arg1);
		argsList.add(arg2);
		argsList.add(arg3);
		BotUtils.addFolder(null, "uniqueUserID", argsList);
		
		assertEquals(testHashMap, BotUtils.keywordToPath);
	}

}
