import java.io.*;
import java.util.*;

public class MessageViewerUser implements SocialMediaUser{

    private String name;
    private String username;
    private String password;
    private ArrayList<MessageViewerUser> friends;
    private ArrayList<MessageViewerUser> blocked;
    private final String friendsFile;
    private final String blockedFile;


    public MessageViewerUser(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.friendsFile = "friends_list_" + username + ".dat" ;
        this.blockedFile = "blocked_list_" + username + ".dat" ;
        this.friends = loadFriendsFromFriendsFile();
        this.blocked = loadBlockedFromBlockedFile();
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

    public void setBlocked(ArrayList<MessageViewerUser> blockedFriends) {
        this.blocked = blockedFriends;
        saveBlockedToBlockedFile(); //running the saving method here to update the file
    }

     public void addFriend(MessageViewerUser friend){
        if(friends.contains(friend)){
            System.out.println("This user is already a friend!"); //message if friends is already in the friends arrayList
        } else {
            friends.add(friend); //fixed bug here.  ArrayList<MessageViewerUser> --> friends
        }
        saveFriendsToFriendsFile(); //saving after adding a friend
    }

    public void removeFriend(MessageViewerUser friend){
        if(!friends.contains(friend)){
            System.out.println("This user is not in the friends list!"); //message if friends is not in the friends arrayList
        } else {
            friends.remove(friend); //fixed bug here.  ArrayList<MessageViewerUser> --> friends
        }
        saveFriendsToFriendsFile(); //saving after removing a friend
    }  

   public boolean blockUser(MessageViewerUser user) throws BlockedUserException {
        if (blocked.contains(user)){
            throw new BlockedUserException("This user is already blocked");
            //added keyword new to create new class object for the exception
        }
        blocked.add(user);
        saveBlockedToBlockedFile(); //saving here after blocking a user
        return blocked.contains(user); //why are we making this method return a boolean?
    }

   public boolean unblockUser(MessageViewerUser user) throws BlockedUserException{
        if (blocked.contains(user)){
            blocked.remove(user);
            saveBlockedToBlockedFile(); //saving here after unblocking a user
            return blocked.contains(user);
        }else {
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
	
    public ArrayList<MessageViewerUser> loadFriendsFromFriendsFile(){

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(friendsFile))) {
            return (ArrayList<MessageViewerUser>)  ois.readObject(); //casting to ArrayList<Message> type object

        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>(); // Return empty list if file doesn't exist
        }
    }

    public ArrayList<MessageViewerUser> loadBlockedFromBlockedFile(){

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

}
