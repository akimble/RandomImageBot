package randomImageBot;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class BotUtilsTest {

	@Test
	public void testAddAdmin() {
		ArrayList<String> argsList = new ArrayList<>();
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
	}

}
