import java.util.List;

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
