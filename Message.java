import java.io.Serializable;
import java.util.ArrayList;

/**
 * Message - Represents a message exchanged between users, containing content, sender, and receiver details.
 *
 * @author L10-Team 1
 * @version November 3, 2024
 */
public class Message implements MessageInterface, Serializable {

    // fields

    String content = ""; //content of the message
    MessageViewerUser sender; //user that sends the message
    MessageViewerUser receiver; //user that recieves the message

    // constructors


    public Message(String content, MessageViewerUser sender, MessageViewerUser receiver) {

        //init
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
    }

    public Message(String content, String username) {
        this.content = content;

    }

    public Message(String content, MessageViewerUser sender) {
        this.content = content;
        this.sender = sender;
    }


    //getter methods for Message objects

    //gets content of a message
    public String getContent() {
        return content;
    }

    //gets the sender of a message
    public MessageViewerUser getSender() {
        return sender;
    }

    //gets the reciever of a message
    public MessageViewerUser getReceiver() {
        return receiver;
    }

    //setter methods

    //sets the content of a message
    public void setContent(String content) {
        this.content = content;
    }

    //sets the sender of a message
    public void setSender(MessageViewerUser sender) {
        this.sender = sender;
    }

    //sets the reciever of a message
    public void setReceiver(MessageViewerUser receiver) {
        this.receiver = receiver;
    }


    @Override
    public String toString() {
        return content; // Return the content of the message
    }

}

