package controller;

import model.Message;
import model.MessageViewerUser;

import java.util.ArrayList;
import java.util.Map;

public interface ControllerInterface {
    void exitApplication();
    String login(String username, String password);
    void logout();
    String createNewAccount(String name, String username, String password);
    void sendMessage(String recipientUsername, String content);
    boolean isMyFriend(MessageViewerUser user, MessageViewerUser searchedUser);
    boolean addFriend(String friendUsername);
    void removeFriend(String friendUsername);
    void blockUser(String blockedUsername);
    void unblockUser(String blockedUsername);
    Map<String, String> getMessages(MessageViewerUser user);
    Map<String, String> getMessageThread(MessageViewerUser user);
    ArrayList<Message> getMessagesFor(MessageViewerUser user, MessageViewerUser otherUser);
}
