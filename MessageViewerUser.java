import java.io.*;
import java.util.*;
import java.util.ArrayList;

/**
 * MessageViewerUser - A class that implements the SocialMediaUser interface,
 * representing a user in the MessageViewer social media application.
 * This class manages user information, friend and blocked user lists,
 * message sending and receiving functionality, and user search capabilities.
 * It also handles the serialization of current users, friends, and blocked lists
 * to maintain persistent data storage.
 *
 * @version November 3, 2024
 * @author L10-Team 1
 */
public class MessageViewerUser implements SocialMediaUser, Serializable {

    private String name;
    private String username;
    private String password;
    private ArrayList<MessageViewerUser> friends;
    private ArrayList<MessageViewerUser> blocked;
    private final ArrayList<Message> sentMessages = new ArrayList<>();
    private final ArrayList<Message> receivedMessages = new ArrayList<>();
    private final ArrayList<MessageThread> messageThreads = new ArrayList<>();
    private final String friendsFile;
    private final String blockedFile;

    private static ArrayList<MessageViewerUser> currentUsers
            = loadCurrentUsersFromFile(); //arraylist of current users

    private static String currentUsersFile = "current_users.dat";

//constructor

    public MessageViewerUser(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.friendsFile = "friends_list_" + username + ".dat" ;
        this.blockedFile = "blocked_list_" + username + ".dat" ;
        this.friends = loadFriendsFromFriendsFile();
        this.blocked = loadBlockedFromBlockedFile();
    }

    // Method to add a user to currentUsers list and save

    public void addUserToCurrentUsers(MessageViewerUser user) {
        currentUsers.add(user);
        saveCurrentUsersToFile(); // Save updated list to file
    }

    // Method to save the currentUsers list to a file

    private static void saveCurrentUsersToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(currentUsersFile))) {
            oos.writeObject(currentUsers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to load currentUsers list from a file (for initial setup)
    private static ArrayList<MessageViewerUser> loadCurrentUsersFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(currentUsersFile))) {
            return (ArrayList<MessageViewerUser>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>(); // Return an empty list if file doesn't exist or error occurs
        }
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
        this.friends = loadFriendsFromFriendsFile();
        return friends;
    }

    public void setFriends(ArrayList<MessageViewerUser> friends) {
        this.friends = friends;
        saveFriendsToFriendsFile(); //running the saving method here to update the file
    }

    public ArrayList<MessageViewerUser> getBlocked() {
        this.blocked = loadBlockedFromBlockedFile();
        return blocked;
    }

//IMPORTANT

    // setBlocked method doesnt remove the blockedFriends from the friends list right now

    public void setBlocked(ArrayList<MessageViewerUser> blockedFriends) {
        this.blocked = blockedFriends;
        saveBlockedToBlockedFile(); //running the saving method here to update the file
    }

    public void addFriend(MessageViewerUser friend) {
        if (friends.contains(friend)) {
            System.out.println("This user is already a friend!");
        } else {
            friends.add(friend); //fixed bug here.  ArrayList<MessageViewerUser> --> friends
        }
        saveFriendsToFriendsFile(); //saving after adding a friend
    }

    public void removeFriend(MessageViewerUser friend) {
        if (!friends.contains(friend)) {
            System.out.println("This user is not in the friends list!");
        } else {
            friends.remove(friend); //fixed bug here.  ArrayList<MessageViewerUser> --> friends
        }
        saveFriendsToFriendsFile(); //saving after removing a friend
    }

    public void blockUser(MessageViewerUser user) throws BlockedUserException {
        if (blocked.contains(user)) {
            throw new BlockedUserException("This user is already blocked");
            //added keyword new to create new class object for the exception
        }

        if (friends.contains(user)) {
            friends.remove(user); // Remove user from friends list if they are friends
            saveFriendsToFriendsFile(); // Update friends list file after removal
        }

        blocked.add(user);
        saveBlockedToBlockedFile(); //saving here after blocking a user
    }

    public void unblockUser(MessageViewerUser user) throws BlockedUserException {
        if (blocked.contains(user)) {
            blocked.remove(user);
            saveBlockedToBlockedFile(); //saving here after unblocking a user
        } else {
            throw new BlockedUserException("This user is not blocked");
            //added keyword new to create new class object for the exception
        }
    }

    //SAVING METHODS

    public void saveFriendsToFriendsFile() {

        //try with resources blcok

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(friendsFile))) {
            oos.writeObject(friends); //replacing the old set of messages with the new set of messages in the filePath
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void saveBlockedToBlockedFile() {

        //try with resources blcok

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(blockedFile))) {
            oos.writeObject(blocked); //replacing the old set of messages with the new set of messages in the filePath
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    // LOADING METHODS

    public ArrayList<MessageViewerUser> loadFriendsFromFriendsFile() {

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(friendsFile))) {
            return (ArrayList<MessageViewerUser>)  ois.readObject(); //casting to ArrayList<Message> type object

        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>(); // Return empty list if file doesn't exist
        }
    }

    public ArrayList<MessageViewerUser> loadBlockedFromBlockedFile() {

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(blockedFile))) {
            return (ArrayList<MessageViewerUser>)  ois.readObject(); //casting to ArrayList<Message> type object

        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>(); // Return empty list if file doesn't exist
        }
    }

    // getter methods for the FriendsFile path and BlockedFile path

    public String getFriendsFile() {
        return friendsFile;
    }

    public String getBlockedFile() {
        return blockedFile;
    }

    //////////
    // Messaging Methods
    public MessageThread getMessageThread(MessageViewerUser otherUser) {
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
        synchronized (blocked) {
            if (blocked.contains(recipient) || recipient.blocked.contains(this)) {
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


    // method to search for a user based on their username
    public MessageViewerUser searchUser(String susername) throws InvalidUsernameException {
        for (MessageViewerUser user : currentUsers) {
            if (user.getUsername().equals(susername)) {
                if (user.getBlocked().contains(this)) {
                    //checks if the user you are searching for has you blocked or not
                    throw new InvalidUsernameException("You cannot search for this user as they have blocked you.");
                } else {
                    return user;
                }
            }
        }
        throw new InvalidUsernameException("Please enter a valid username");
    }


    // method to view searched viewer's profile
    public String userViewer(String searchedUsername) throws InvalidUsernameException {
        MessageViewerUser user = searchUser(searchedUsername);
        StringBuilder userInfo = new StringBuilder();

        if (user == null) {
            throw new InvalidUsernameException("username cannot be empty!");

        } else if (blocked.contains(user)) {
            // If the user is blocked, show limited information
            userInfo.append("User is blocked.\n");
            userInfo.append("Name: ").append(user.getName()).append("\n");
            userInfo.append("Username: ").append(user.getUsername()).append("\n");
        } else {
            // Show detailed information if the user is not blocked
            userInfo.append("Name: ").append(user.getName()).append("\n");
            userInfo.append("Username: ").append(user.getUsername()).append("\n");


            if (friends.contains(user)) {
                userInfo.append("Status: Friend");
            } else {
                userInfo.append("Status: Not a friend");
            }
        }
        return userInfo.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MessageViewerUser that = (MessageViewerUser) obj;
        return username.equals(that.username); // username uniquely identifies a user
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }


}

