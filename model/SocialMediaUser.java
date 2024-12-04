package model;

import java.util.List;


/**
 * model.SocialMediaUser - An interface representing a user in the social media application,
 * defining essential user functionalities such as friend management, messaging, and profile viewing.
 * 
 * @author L10-Team 1
 * @version November 3, 2024
 */
public interface SocialMediaUser {

    // Basic user information
    String getName();
    void setName(String name);

    String getUsername();
    void setUsername(String username);

    String getPassword();
    void setPassword(String password);

    // Friend and Block management
    List<MessageViewerUser> getFriends();
    void addFriend(MessageViewerUser friend);
    void removeFriend(MessageViewerUser friend);

    List<MessageViewerUser> getBlocked();
    void blockUser(MessageViewerUser user) throws BlockedUserException;
    void unblockUser(MessageViewerUser user) throws BlockedUserException;

    // Messaging 
    void sendMessage(MessageViewerUser recipient, String content) throws BlockedUserException;
    MessageThread getMessageThread(MessageViewerUser otherUser);

    // User search
    MessageViewerUser searchUser(String username) throws InvalidUsernameException;

    // View user profile
    String userViewer(String searchedUsername) throws InvalidUsernameException;
}
