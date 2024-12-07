import model.MessageViewerUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.util.*;
/**
* This test class verifies the functionality of the server methods, including user authentication,
* file-based user persistence, and handling of edge cases such as missing or invalid data files.
*
* @author L10-Team1
* @version November 17, 2024
* 
*/
class MessageViewerServerTest {

    private MessageViewerServer server;

    @BeforeEach
    void setUp() {
        server = new MessageViewerServer();
    }

    @Test
    void testLoadUsersFromFile_ValidFile() {
        // Prepare a valid file with users data for testing
        ArrayList<MessageViewerUser> users = new ArrayList<>();
        users.add(new MessageViewerUser("Alice", "alice123", "password123"));
        users.add(new MessageViewerUser("Bob", "bob456", "password456"));

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("current_users.dat"))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Failed to write test file");
        }

        ArrayList<MessageViewerUser> loadedUsers = server.loadUsersFromFile();
        assertEquals(2, loadedUsers.size(), "Should load 2 users from the file");
        assertEquals("alice123", loadedUsers.get(0).getUsername(), "First user should be alice123");
        assertEquals("bob456", loadedUsers.get(1).getUsername(), "Second user should be bob456");
    }

    @Test
    void testLoadUsersFromFile_NoFile() {
        // Test when the file doesn't exist
        new File("current_users.dat").delete(); // Ensure file is deleted for the test

        ArrayList<MessageViewerUser> loadedUsers = server.loadUsersFromFile();
        assertTrue(loadedUsers.isEmpty(), "Should return an empty list if file doesn't exist");
    }

    @Test
    void testSaveUsersToFile() {
        // Test saving users to a file
        ArrayList<MessageViewerUser> users = new ArrayList<>();
        users.add(new MessageViewerUser("Charlie", "charlie789", "password789"));

        server.saveUsersToFile(); // This method will try to save the currentUsers to the file

        ArrayList<MessageViewerUser> loadedUsers = server.loadUsersFromFile();
        assertEquals(1, loadedUsers.size(), "Should save and load 1 user");
        assertEquals("Charlie", loadedUsers.get(0).getUsername(), "User should be Charlie");
    }

    @Test
    void testAuthenticateUser_Success() {
        // Mock currentUsers to contain a valid user for testing
        MessageViewerUser validUser = new MessageViewerUser("David", "david123", "password123");
        server.currentUsers.add(validUser);

        MessageViewerUser authenticatedUser = MessageViewerServer.ClientHandler.authenticateUser("david123", "password123");
        assertNotNull(authenticatedUser, "Authentication should succeed with correct username and password");
        assertEquals("David", authenticatedUser.getUsername(), "Authenticated user should be David");
    }

    @Test
    void testAuthenticateUser_Fail_TooManyAttempts() {
        // Mock currentUsers to contain a valid user
        MessageViewerUser validUser = new MessageViewerUser("Eva", "eva123", "password123");
        server.currentUsers.add(validUser);

        // Test incorrect credentials with multiple attempts
        MessageViewerUser authenticatedUser = MessageViewerServer.ClientHandler.authenticateUser("eva123", "wrongpassword");
        assertNull(authenticatedUser, "Authentication should fail after too many attempts");
    }

    @Test
    void testAuthenticateUser_Fail_UserNotFound() {
        // Test authentication failure when the user does not exist
        MessageViewerUser authenticatedUser = MessageViewerServer.ClientHandler.authenticateUser("nonexistentuser", "password");
        assertNull(authenticatedUser, "Authentication should fail if the user is not found");
    }

    @Test
    void testAuthenticateUser_Fail_IncorrectPassword() {
        // Mock currentUsers to contain a valid user
        MessageViewerUser validUser = new MessageViewerUser("Frank", "frank123", "password123");
        server.currentUsers.add(validUser);

        // Test authentication failure with incorrect password
        MessageViewerUser authenticatedUser = MessageViewerServer.ClientHandler.authenticateUser("frank123", "wrongpassword");
        assertNull(authenticatedUser, "Authentication should fail with incorrect password");
    }
    
    @Test
    void testCreateAccount_UsernameAlreadyExists() throws IOException, ClassNotFoundException {
        // Prepare the existing user in the currentUsers list
        MessageViewerUser existingUser = new MessageViewerUser("Existing User", "existing123", "password123");
        server.currentUsers.add(existingUser);

        // Prepare the streams you want to inject
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteArrayOutputStream);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
            ("New User\nexisting123\npassword123").getBytes()); // Attempt to create with existing username
        ObjectInputStream in = new ObjectInputStream(byteArrayInputStream);

        // Use the overloaded constructor to inject streams
        MessageViewerServer.ClientHandler clientHandler = server.new ClientHandler(in, out);

        // Call the handleCreateAccount method
        clientHandler.handleCreateAccount();

        // Capture and assert the response from the server
        String output = byteArrayOutputStream.toString().trim();
        assertEquals("Username already exists. Please choose another.", output, "Should return the 'username already exists' message");
    }

    
}
