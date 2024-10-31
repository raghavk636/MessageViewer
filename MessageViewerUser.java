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
        ArrayList<MessageViewerUser>.add(friend);
    }

    public void removeFriend(MessageViewerUser friend){
        ArrayList<MessageViewerUser>.remove(friend);
    }

    public boolean blockUser(MessageViewerUser user){
        if (blocked.contains(user)){
            throw BlockedUserException("This user is already blocked");
            return false;
        }
        blocked.add(user);
        return blocked.contains(user);
    }

    public boolean unblockUser(MessageViewerUser user){
        if (blocked.contains(user)){
            blocked.remove(user);
            return blocked.contains(user);
        }else {
	    throw BlockedUserException("This user is not blocked");
            return false;
        }
    }

}
