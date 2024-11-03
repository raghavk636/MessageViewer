import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class MessageViewerUserTestCases {
    private MessageViewerUser userA;
    private MessageViewerUser userB;

    // Creates test users
    @Before
    public void setUp() {
        userA = new MessageViewerUser("UserA", "a123", "password");
        userB = new MessageViewerUser("UserB", "b123", "password");
    }

    // Removes all the created files after the test is done
    @After
    public void clearAll() {
        new File("friends_list_a123.dat").delete();
        new File("blocked_list_a123.dat").delete();
        new File("friends_list_b123.dat").delete();
        new File("blocked_list_b123.dat").delete();
        new File("current_users.dat").delete();
    }

    @Test
    public void testAddFriend() {
        userA.addFriend(userB);
        assertTrue(userA.getFriends().contains(userB));
    }

    @Test
    public void testRemoveFriend() {
        userA.addFriend(userB);
        userA.removeFriend(userB);
        assertFalse(userA.getFriends().contains(userB));
    }

    @Test(expected = BlockedUserException.class)
    public void testBlockUserException() throws BlockedUserException {
        userA.blockUser(userB);
        userA.blockUser(userB);  // Attempt to block again should throw exception
    }

    @Test
    public void testBlockUser() throws BlockedUserException {
        userA.addFriend(userB);
        userA.blockUser(userB);

        assertTrue(userA.getBlocked().contains(userB));
        assertFalse(userA.getFriends().contains(userB));  // Verify userB is removed from friends list
    }

    @Test(expected = BlockedUserException.class)
    public void testUnblockUserException() throws BlockedUserException {
        userA.unblockUser(userB);  // Attempt to unblock a non-blocked user should throw exception
    }

    @Test
    public void testUnblockUser() throws BlockedUserException {
        userA.blockUser(userB);
        userA.unblockUser(userB);

        assertFalse(userA.getBlocked().contains(userB));
    }

    @Test
    public void testSendMessage() throws BlockedUserException {
        userA.sendMessage(userB, "Hello, UserB!");
        assertEquals("Hello, UserB!", userA.getSentMessages().get(0).getContent());
        assertEquals("Hello, UserB!", userB.getReceivedMessages().get(0).getContent());
    }

    @Test(expected = BlockedUserException.class)
    public void testSendMessageBlockedUser() throws BlockedUserException {
        userA.blockUser(userB);
        userB.sendMessage(userA, "Hello, UserA!");  // Should throw exception
    }

    @Test
    public void testSearchUser() throws InvalidUsernameException {
        assertEquals(userB, userA.searchUser("b123"));
    }

    @Test(expected = InvalidUsernameException.class)
    public void testSearchInvalidUser() throws InvalidUsernameException {
        userA.searchUser("nonexistentUser");  // Should throw exception
    }

    @Test(expected = InvalidUsernameException.class)
    public void testSearchBlockedUser() throws InvalidUsernameException, BlockedUserException {
        userB.blockUser(userA);
        userA.searchUser("b123");  // Should throw exception because userA is blocked by userB
    }

    @Test
    public void testUserViewer() throws InvalidUsernameException, BlockedUserException {
        userA.addFriend(userB);
        userB.blockUser(userA); // Added for the sake of completeness

        String friendProfile = userA.userViewer("b123");
        assertTrue(friendProfile.contains("Status: Friend"));

        String blockedProfile = userB.userViewer("a123");
        assertTrue(blockedProfile.contains("User is blocked"));
    }
}
