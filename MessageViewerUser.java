import java.io.*;
import java.util.*;

public class MessageViewerUser implements SocialMediaUser{

    private String name;
    private String username;
    private String password;
    private ArrayList<MessageViewerUser> friends;
    private ArrayList<MessageViewerUser> blocked;

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
