import java.util.ArrayList;

public interface MessageThreadInterface {


    //METHODS

    void addMessage(String content, MessageViewerUser sender);

    void saveMessagesToFile();

    ArrayList<Message> loadMessagesFromFile();

    String toString();

    MessageViewerUser getUser1();

    MessageViewerUser getUser2();

}
