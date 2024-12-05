package controller;

import model.Message;
import model.MessageViewerUser;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/*
Class to act as a mediator between client and server
 */
public class Controller implements ControllerInterface {
    Socket socket;
    ObjectOutputStream outputStream;
    ObjectInputStream inputStream;
    private static final String SERVER_ADDRESS = "localhost"; // Server address
    private static final int SERVER_PORT = 12345;

    public Controller() {
        try{
            this.socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            System.out.println("Error in establishing client conenction:" + e.getMessage());

        }
    }

    public void exitApplication() {
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Error in closing connection:" + e.getMessage());
        }
    }

    public String login(String username, String password) {
        try {
            outputStream.writeObject("LOGIN_ACCOUNT"); // Inform the server about the action
            outputStream.writeObject(username); // Send username to server
            outputStream.writeObject(password); // Send password to server
            String loginResponse = (String) inputStream.readObject();
            System.out.println(loginResponse);
            return loginResponse;
        } catch (Exception e) {
            System.out.println("Error in new account creation:" + e.getMessage());
        }

        // Handle the account creation prompts and responses
        return "null";
    }

    public void logout() {
        try {
            outputStream.writeObject("LOGOUT");
            String logoutResponse = (String) inputStream.readObject();
            System.out.println("logged out");
        } catch (Exception e) {
           System.out.println("Error in logging out:" + e.getMessage());
        }
    }

    /* calling this method with the create account button

     */

    public String createNewAccount(String name, String username, String password) {
        try {
            outputStream.writeObject("CREATE_ACCOUNT"); // Inform the server about the action
            outputStream.writeObject(name); // Send name to server
            outputStream.writeObject(username); // Send username to server
            outputStream.writeObject(password); // Send password to server
            String loginResponse = (String) inputStream.readObject();
            System.out.println(loginResponse);
            return loginResponse;

        } catch (Exception e) {
            System.out.println("Error in new account creation:" + e.getMessage());
        }

        // Handle the account creation prompts and responses
        return "";
    }

    public void sendMessage(String recipientUsername, String content) {
        try {
            outputStream.writeObject("SEND_MESSAGE");
            outputStream.writeObject(recipientUsername);
            outputStream.writeObject(content);
            String messageResponse = (String) inputStream.readObject();
        } catch (Exception e) {

        }
    }

    public boolean isMyFriend(MessageViewerUser user, MessageViewerUser searchedUser) {
        for(MessageViewerUser friend: user.getFriends()){
            if(friend.getUsername().equals(searchedUser.getUsername())){
                return true;
            }
        }
        return false;
    }
    public boolean addFriend(String friendUsername) {
        try {
            outputStream.writeObject("ADD_FRIEND"); // Inform the server about the action
            outputStream.writeObject(friendUsername); // Send friendUsername to server
            String result = (String) inputStream.readObject();
            if(!result.equals("false"))
                return true;
        } catch (Exception e) {
            System.out.println("Couldnt add friend" + e.getMessage());
        }
        return false;
    }

    public void removeFriend(String friendUsername) {
        try {
            outputStream.writeObject("REMOVE_FRIEND"); // Inform the server about the action
            outputStream.writeObject(friendUsername); // Send friendUsername to server

        } catch (Exception e) {
            System.out.println("Couldnt remove friend" + e.getMessage());
        }

    }

    public void blockUser(String blockedUsername) {
        try {
            outputStream.writeObject("BLOCK_USER"); // Inform the server about the action
            outputStream.writeObject(blockedUsername); // Send blockedusername to server

        } catch (Exception e) {
            System.out.println("Couldnt block" + e.getMessage());
        }

    }

    public void unblockUser(String blockedUsername) {
        try {
            outputStream.writeObject("UNBLOCK_USER"); // Inform the server about the action
            outputStream.writeObject(blockedUsername); // Send blockedusername to server

        } catch (Exception e) {
            System.out.println("Couldnt block" + e.getMessage());
        }

    }

    public Map<String, String> getMessages(MessageViewerUser user) {
        Map<String, String> results = new HashMap<String, String>();
        System.out.println("in controller" + user.getUsername());
        if(user==null)
            return results;
        user.allSentAndReceivedMessages().forEach((key, value) -> results.put(key.toString(), value.getContent()));

        results.forEach((String key1, String value1) -> System.out.println("key is:" + key1 + "---val" + value1));
        return results;
    }


    public Map<String, String> getMessageThread(MessageViewerUser user) {
        Map<String, String> results = new HashMap<String, String>();
        System.out.println("in controller" + user.getUsername());
        if(user==null)
            return results;
        user.allSentAndReceivedMessages().forEach((key, value) -> results.put(key.toString(), value.getContent()));

        results.forEach((String key1, String value1) -> System.out.println("key is:" + key1 + "---val" + value1));
        return results;
    }


    public ArrayList<Message> getMessagesFor(MessageViewerUser user, MessageViewerUser otherUser) {
        ArrayList<Message> results = new ArrayList<>();
        results= user.getAllMessagesFor(otherUser);
        results.sort(Comparator.comparing(Message::getDate));
        return results;
     }
}
