/**
 * MessageInterface - An interface defining methods for managing message content, sender, and receiver information.
 * 
 * @author L10-Team 1 
 * @version November 3, 2024
 */
public interface MessageInterface {

    //METHODS

    String getContent();
    void setContent(String content);
    MessageViewerUser getSender();
    void setSender(MessageViewerUser sender);
    MessageViewerUser getReceiver();
    void setReceiver(MessageViewerUser receiver);
} 
