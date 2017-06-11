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
		String arg6;
		
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
		arg6 = "C:/pictures/new folder";
		
		// Test for "\\" in folder path (second argument)
		argsList.add(arg0);
		argsList.add(arg1);
		BotUtils.addFolder(null, "uniqueUserID", argsList);
		testHashMap.put(arg0, arg1);
		
		assertEquals(testHashMap, BotUtils.hashMap);
		
		// Test for only "/" in folder path
		argsList.clear();
		argsList.add(arg2);
		argsList.add(arg3);
		BotUtils.addFolder(null, "uniqueUserID", argsList);
		testHashMap.put(arg2, arg3);
		
		assertEquals(testHashMap, BotUtils.hashMap);
		
		// Test for "\\" and "/" in folder path
		argsList.clear();
		argsList.add(arg2);
		argsList.add(arg4);
		BotUtils.addFolder(null, "uniqueUserID", argsList);
		testHashMap.put(arg2, arg4);
		
		assertEquals(testHashMap, BotUtils.hashMap);
		
		// Test for spaces in folder path
		argsList.clear();
		argsList.add(arg2);
		argsList.add(arg5);
		BotUtils.addFolder(null, "uniqueUserID", argsList);
		testHashMap.put(arg2, arg5);
		
		assertEquals(testHashMap, BotUtils.hashMap);
		
		// Test for spaces in the folder path
		argsList.clear();
		argsList.add(arg2);
		argsList.add(arg6);
		BotUtils.addFolder(null, "uniqueUserID", argsList);
		testHashMap.put(arg2, arg6);
		
		assertEquals(testHashMap, BotUtils.hashMap);
		
		// Test with no arguments
		argsList.clear();
		BotUtils.addFolder(null, "uniqueUserID", argsList);
		
		assertEquals(testHashMap, BotUtils.hashMap);
		
		// Test with more than 2 arguments
		argsList.add(arg0);
		argsList.add(arg1);
		argsList.add(arg2);
		argsList.add(arg3);
		BotUtils.addFolder(null, "uniqueUserID", argsList);
		
		assertEquals(testHashMap, BotUtils.hashMap);
	}

}
