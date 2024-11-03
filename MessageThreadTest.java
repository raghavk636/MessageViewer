import org.junit.jupiter.api.*;
import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MessageThreadTest {

    private static final String USER1 = "Alice";
    private static final String USER2 = "Bob";
    private MessageThread messageThread;
    private MessageViewerUser user1;
    private MessageViewerUser user2;

    @BeforeEach
    public void setUp() {
        user1 = new MessageViewerUser(USER1);
        user2 = new MessageViewerUser(USER2);
        messageThread = new MessageThread(user1, user2);
    }

    @Test
    public void testAddMessage() {
        String content = "Hello, Bob!";
        messageThread.addMessage(content, user1);

        // Verify that the message has been added
        ArrayList<Message> messages = messageThread.loadMessagesFromFile();
        assertEquals(1, messages.size());
        assertEquals(content, messages.get(0).getContent());
        assertEquals(USER1, messages.get(0).getSender());
    }

    @Test
    public void testLoadMessagesFromFile() {
        // Adding a message to simulate loading from file
        messageThread.addMessage("Hello, Bob!", user1);

        // Create a new MessageThread instance to load messages
        MessageThread newMessageThread = new MessageThread(user1, user2);
        ArrayList<Message> messages = newMessageThread.loadMessagesFromFile();

        assertEquals(1, messages.size());
        assertEquals("Hello, Bob!", messages.get(0).getContent());
        assertEquals(USER1, messages.get(0).getSender());
    }

    @Test
    public void testToString() {
        messageThread.addMessage("Hello, Bob!", user1);
        messageThread.addMessage("Hi, Alice!", user2);

        String expectedOutput = "Message{content='Hello, Bob!', sender='Alice'}\n" +
                                "Message{content='Hi, Alice!', sender='Bob'}\n";

        assertEquals(expectedOutput, messageThread.toString());
    }

    @AfterEach
    public void tearDown() {
        // Clean up any files created during testing
        File file = new File("chat_history_" + USER1 + "_" + USER2 + ".dat");
        if (file.exists()) {
            file.delete();
        }
    }
}
