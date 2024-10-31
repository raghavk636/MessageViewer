import java.io.*;
import java.util.*;

public class MessageViewerUser implements SocialMediaUser{

    private String name;
    private String username;
    private String password;
    private ArrayList<MessageViewerUser> friends;
    private ArrayList<MessageViewerUser> blocked;
    private final ArrayList<Message> sentMessages = new ArrayList<>();
    private final ArrayList<Message> receivedMessages = new ArrayList<>();
    private final ArrayList<MessageThread> messageThreads = new ArrayList<>();


    public MessageViewerUser(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
        friends = new ArrayList<MessageViewerUser>();
        blocked = new ArrayList<MessageViewerUser>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
	
    public List<Message> getSentMessages() {
        return new ArrayList<>(sentMessages);
    }
    
    public List<Message> getReceivedMessages() {
        return new ArrayList<>(receivedMessages);
    }
    
    public List<MessageThread> getMessageThreads() {
        return new ArrayList<>(messageThreads);
    }

    public ArrayList<MessageViewerUser> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<MessageViewerUser> friends) {
        this.friends = friends;
    }

    public ArrayList<MessageViewerUser> getBlocked() {
        return blocked;
    }

    public void setBlocked(ArrayList<MessageViewerUser> blockedFriends) {
        this.blocked = blockedFriends;
    }

    public void addFriend(MessageViewerUser friend){
	    if(friends.contains(friend)){
		    System.out.println("This user is already a friend!"); //message if friends is already in the friends arrayList
	    } else {
        friends.add(friend); //fixed bug here.  ArrayList<MessageViewerUser> --> friends
	    }
    }

    public void removeFriend(MessageViewerUser friend){
	        if(!friends.contains(friend)){
		    System.out.println("This user is not in the friends list!"); //message if friends is not in the friends arrayList
	    } else {
        friends.remove(friend); //fixed bug here.  ArrayList<MessageViewerUser> --> friends
	    }
    }  

    public boolean blockUser(MessageViewerUser user){
        if (blocked.contains(user)){
            throw new BlockedUserException("This user is already blocked"); //added keyword new to create new class object for the exception
            return false;
        }
        blocked.add(user);
        return blocked.contains(user); //why are we making this method return a boolean?
    }

    public boolean unblockUser(MessageViewerUser user){ 
        if (blocked.contains(user)){
            blocked.remove(user);
            return blocked.contains(user);
        }else {
	    throw new BlockedUserException("This user is not blocked"); //added keyword new to create new class object for the exception
            return false; //I think if the exception is thrown this line doesnt get executed or smth? 
        }
    }

}
// Messaging Methods
    private MessageThread getMessageThread(MessageViewerUser otherUser) {
        synchronized (messageThreads) {
            for (MessageThread thread : messageThreads) {
                if ((thread.getUser1().equals(this) && thread.getUser2().equals(otherUser)) ||
                        (thread.getUser1().equals(otherUser) && thread.getUser2().equals(this))) {
                    return thread;
                }
            }
            // If theres no existing threads, then create a new one
            MessageThread newThread = new MessageThread(this, otherUser);
            messageThreads.add(newThread);
            return newThread;
        }
    }

    public void sendMessage(MessageViewerUser recipient, String content) throws BlockedUserException {
        // Check if the person is blocked
        synchronized (blockedFriends) {
            if (blockedFriends.contains(recipient) || recipient.blockedFriends.contains(this)) {
                throw new BlockedUserException("Blocked cant send the message.");
            }
        }

        // Create msg and add it to the correct thread
        MessageThread thread;
        synchronized (messageThreads) {
            thread = getMessageThread(recipient);
        }
        thread.addMessage(content, this);

        // Track sent and received messages
        Message message = new Message(content, this, recipient);
        synchronized (sentMessages) {
            sentMessages.add(message);
        }
        synchronized (recipient.receivedMessages) {
            recipient.receivedMessages.add(message);
        }
    }
}
