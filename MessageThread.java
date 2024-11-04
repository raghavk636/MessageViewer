import java.io.*;
import java.util.ArrayList;

/**
 * MessageThread - Manages a conversation thread between two users, allowing messages to be
 * added, saved, and loaded from a file. Each thread has a unique file path for chat history storage.
 *  
 * @version November 3, 2024
 * @author L10-Team 1
 */
public class MessageThread implements MessageThreadInterface, Serializable {

    //fields


    private MessageViewerUser user1;
    private MessageViewerUser user2;
    private ArrayList<Message> messages; //arraylist of messages that go in the chat history file
    private String filePath; //filepath where the chathistory is stored and loaded from


    public MessageThread(MessageViewerUser user1, MessageViewerUser user2) {
        this.user1 = user1;
        this.user2 = user2;

        this.filePath = "chat_history_" + user1.getUsername() + "_" + user2.getUsername() + ".dat";

        //making the name for the dat file where the messages are stored


        this.messages = loadMessagesFromFile(); // Load existing messages on startup
    }

    // Add message to the chat and save to file

    public void addMessage(String content, MessageViewerUser sender) {
        Message message = new Message(content, sender.getUsername());
        messages.add(message);
        saveMessagesToFile(); // Save whenever a new message is added
    }

    // Save messages to a file
    public void saveMessagesToFile() {

        //try with resources blcok

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(messages); //replacing the old set of messages with the new set of messages in the filePath
        } catch (IOException e) {
            e.printStackTrace();
        }

        // new FileOutputStream(filePath)) opens a file at the specified filePath for writing there.

        //filePath is the specific DM thread between 2 users

        //ObjectOutputStream makes it write objects which we want because its writing the ArrayList object "messages"
        //to the filePath
    }


    // Load messages from a file
    public ArrayList<Message> loadMessagesFromFile() {


        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (ArrayList<Message>)  ois.readObject(); //casting to ArrayList<Message> type object

        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>(); // Return empty list if file doesn't exist
        }
    }

    // Display chat history

    public String toString() {
        StringBuilder chatHistory = new StringBuilder(); //init and declaring chatHistory


        for (Message msg : messages) { //for each Message object msg in messages
            chatHistory.append(msg).append("\n"); //appends Message object and and "\n"
            // to the string chatHistory which can be displayed to the actual users
        }
        return chatHistory.toString();
    }

    public MessageViewerUser getUser1() {
        return user1;

    }

    public MessageViewerUser getUser2() {
        return user2;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
