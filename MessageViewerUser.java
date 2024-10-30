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
	
	

}
