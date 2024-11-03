import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import static org.junit.Assert.*;

public class MessageTest {

    private MessageViewerUser sender;
    private MessageViewerUser receiver;
    private Message message;


    //method runs before each test

    @Before
    public void setUp() {
        // Initialize users and a sample message for testing
        sender = new MessageViewerUser("UserA", "A123", "password1");
        receiver = new MessageViewerUser("UserB", "B123", "password2");
        message = new Message("Hello, B!", sender, receiver);
    }


    //method to rest everything after each test
    @After
    public void clearAll() {
        // Set variables to null to ensure a clean state for each test
        sender = null;
        receiver = null;
        message = null;
    }

    // CONSTRUCTOR TEST

    @Test
    public void testConstructorWithAllFields() {
        // Test that the constructor correctly initializes all fields
        assertEquals("Hello, B!", message.getContent());
        assertEquals(sender, message.getSender());
        assertEquals(receiver, message.getReceiver());
    }

    // CONSTRUCTOR WITH THE CONTENT AND USERNAME TEST

    @Test
    public void testConstructorWithContentAndUsername() {
        // Test that the constructor with only content and username
        Message messageWithUsername = new Message("Hi there!", "A");

        assertEquals("Hi there!", messageWithUsername.getContent());
        assertNull(messageWithUsername.getSender());
        assertNull(messageWithUsername.getReceiver());
    }

    // GET CONTENT TEST

    @Test
    public void testGetContent() {
        // Test the getContent method
        assertEquals("Hello, B!", message.getContent());
    }

    // SET CONTENT TEST

    @Test
    public void testSetContent() {
        // Test the setContent method
        message.setContent("Good afternoon, B!");
        assertEquals("Good afternoon, B!", message.getContent());
    }


    //GET SENDER TEST

    @Test
    public void testGetSender() {
        // Test the getSender method
        assertEquals(sender, message.getSender());
    }


    // SET SENDER TEST

    @Test
    public void testSetSender() {

        MessageViewerUser newSender = new MessageViewerUser("UserC", "C123", "password3");
        message.setSender(newSender);
        assertEquals(newSender, message.getSender());
    }

    // GET RECIEVER TEST

    @Test
    public void testGetReceiver() {

        assertEquals(receiver, message.getReceiver());
    }


    //SET RECIEVER TEST

    @Test
    public void testSetReceiver() {

        MessageViewerUser newReceiver = new MessageViewerUser("UserD", "D123", "password4");
        message.setReceiver(newReceiver);
        assertEquals(newReceiver, message.getReceiver());
    }
}
