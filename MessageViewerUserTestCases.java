import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;


public class MessageViewerUserTestCases {
    private MessageViewerUser user1;
    private MessageViewerUser user2;
    private MessageViewerUser user3;


    //creates test users
    @Before
    public void setUp() {
        user1 = new MessageViewerUser("UserA", "A123", "password1");
        user2 = new MessageViewerUser("UserB", "B123", "password2");
        user3 = new MessageViewerUser("UserC", "C123", "password3");
    }


    //Removes all the created files after the test is done
    @After
    public void clearAll() {
        new File("friends_list_A123.dat").delete();
        new File("blocked_list_A123.dat").delete();
        new File("friends_list_B123.dat").delete();
        new File("blocked_list_B123.dat").delete();
        new File("friends_list_C123.dat").delete();
        new File("blocked_list_C123.dat").delete();
        new File("current_users.dat").delete();
    }

    @Test
    public void testAddFriend() {
        user1.addFriend(user2);
        assertTrue(user1.getFriends().contains(user2));
    }

    @Test
    public void testRemoveFriend() {
        user1.addFriend(user2);
        user1.removeFriend(user2);
        assertFalse(user1.getFriends().contains(user2));
    }

    @Test(expected = BlockedUserException.class)
    public void testBlockUserException() throws BlockedUserException {
        user1.blockUser(user2);
        user1.blockUser(user2);  // Attempt to block again should throw exception
    }

    @Test
    public void testBlockUser() throws BlockedUserException {
        user1.addFriend(user2);
        user1.blockUser(user2);

        assertTrue(user1.getBlocked().contains(user2));
        assertFalse(user1.getFriends().contains(user2));  // Verify user2 is removed from friends list
    }

    @Test(expected = BlockedUserException.class)
    public void testUnblockUserException() throws BlockedUserException {
        user1.unblockUser(user2);  // Attempt to unblock a non-blocked user should throw exception
    }

    @Test
    public void testUnblockUser() throws BlockedUserException {
        user1.blockUser(user2);
        user1.unblockUser(user2);

        assertFalse(user1.getBlocked().contains(user2));
    }

    @Test
    public void testSendMessage() throws BlockedUserException {
        user1.sendMessage(user2, "Hello, B!");
        assertEquals("Hello, B!", user1.getSentMessages().get(0).getContent());
        assertEquals("Hello, B!", user2.getReceivedMessages().get(0).getContent());
    }

    @Test(expected = BlockedUserException.class)
    public void testSendMessageBlockedUser() throws BlockedUserException {
        user1.blockUser(user2);
        user2.sendMessage(user1, "Hello, A!");  // Should throw exception
    }

    @Test
    public void testSearchUser() throws InvalidUsernameException {
        // Ensure user2 is added to currentUsers
        user1.addUserToCurrentUsers(user1);
        user1.addUserToCurrentUsers(user2);

        assertEquals(user2, user1.searchUser("B123"));
    }

    @Test(expected = InvalidUsernameException.class)
    public void testSearchInvalidUser() throws InvalidUsernameException {
        user1.searchUser("nonexistentUser");  // Should throw exception
    }

    @Test(expected = InvalidUsernameException.class)
    public void testSearchBlockedUser() throws InvalidUsernameException, BlockedUserException {

        user2.blockUser(user1);
        user1.searchUser("B123");  // Should throw exception because user1 is blocked by user2
    }

    @Test
    public void testUserViewer() throws InvalidUsernameException, BlockedUserException {
        user1.addFriend(user2);


        String friendProfile = user1.userViewer("B123");
        assertTrue(friendProfile.contains("Status: Friend"));

        user1.blockUser(user2);

        String blockedProfile = user1.userViewer("B123");
        assertTrue(blockedProfile.contains("User is blocked"));
    }
}
