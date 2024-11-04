import java.util.ArrayList;

/**
 * MessageThreadInterface - An interface defining methods for managing a message thread
 * between two users, including adding messages, saving, and loading chat history.
 *
 * @version November 3, 2024
 * @author L10-Team 1 
 */
public interface MessageThreadInterface {


    //METHODS

    void addMessage(String content, MessageViewerUser sender);

    void saveMessagesToFile();

    ArrayList<Message> loadMessagesFromFile();

    String toString();

    MessageViewerUser getUser1();

    MessageViewerUser getUser2();

}
