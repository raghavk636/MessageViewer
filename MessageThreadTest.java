import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MessageThreadTest {

    private MessageViewerUser user1;
    private MessageViewerUser user2;
    private MessageThread messageThread;

    @BeforeEach
    public void setUp() {
        user1 = new MessageViewerUser("UserA", "A123", "password1");
        user2 = new MessageViewerUser("UserB", "B123", "password2");

        messageThread = new MessageThread(user1, user2);
    }

    @AfterEach
    public void cleanUp() {
        // Clear the messages file after each test
        File file = new File(messageThread.getFilePath()); // Assuming getFilePath() returns the path to the message file
        if (file.exists()) {
            file.delete(); // Delete the file to reset state
        }
    }

    @Test
    public void testAddMessageAndSaveToFile() {
        // Add a message and check if it is saved in the file
        messageThread.addMessage("Hello, B!", user1);

        // Load messages from file to check if they were saved correctly
        ArrayList<Message> loadedMessages = messageThread.loadMessagesFromFile();
        assertEquals(1, loadedMessages.size());
        assertEquals("Hello, B!", loadedMessages.get(0).getContent());
       
    }

    @Test
    public void testLoadMessagesFromFileWithNoExistingFile() {
        // Ensure that loading from a non-existent file returns an empty list
        ArrayList<Message> loadedMessages = messageThread.loadMessagesFromFile();
        assertTrue(loadedMessages.isEmpty());
    }

    @Test
    public void testLoadMessagesFromFileWithExistingData() {
        // Add a message, save it, and reload to test if data persistence works
        messageThread.addMessage("Hello, B!", user1);
        messageThread.addMessage("Hi, A!", user2);

        // Create a new MessageThread instance to simulate restarting the application
        MessageThread newThread = new MessageThread(user1, user2);
        ArrayList<Message> loadedMessages = newThread.loadMessagesFromFile();

        assertEquals(2, loadedMessages.size());
        assertEquals("Hello, B!", loadedMessages.get(0).getContent());
        assertEquals("Hi, A!", loadedMessages.get(1).getContent());
    }

    @Test
    public void testToString() {
        // Add messages and check the chat history display
        messageThread.addMessage("Hello, B!", user1);
        messageThread.addMessage("How are you?", user1);
        messageThread.addMessage("I'm good, thanks!", user2);

        String expectedOutput = "Hello, B!\n" +
                "How are you?\n" +
                "I'm good, thanks!\n";

        assertEquals(expectedOutput, messageThread.toString());
    }

    @Test
    public void testEmptyChatHistory() {
        // Ensure that when no messages are added, chat history is empty
        String chatHistory = messageThread.toString();
        System.out.println("Chat History: " + chatHistory);
        assertTrue(chatHistory.isEmpty());
    }

    @Test
    public void testGetUser1AndUser2() {
        // Check that users are set correctly
        assertEquals(user1, messageThread.getUser1());
        assertEquals(user2, messageThread.getUser2());
    }

    @Test
    public void testSaveMessagesToFileAndReload() {
        // Test that messages persist even after reloading the MessageThread
        messageThread.addMessage("This is a test message", user1);
        messageThread.saveMessagesToFile();

        // Recreate the MessageThread to simulate reloading
        MessageThread reloadedThread = new MessageThread(user1, user2);
        ArrayList<Message> reloadedMessages = reloadedThread.loadMessagesFromFile();

        assertEquals(1, reloadedMessages.size());
        assertEquals("This is a test message", reloadedMessages.get(0).getContent());
    }
}
