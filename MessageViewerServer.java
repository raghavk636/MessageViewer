import java.io.*;
import java.net.*;
import java.util.*;
/**
 * The MessageViewerServer class is a server created for managing
 * user authentication, messaging, and user interactions. It uses sockets to
 * handle client-server communication and saves user data to a file.
 @author L10-Team 1
 @version November 16th 2024
 */
public class MessageViewerServer implements ServerInterface {
    private static final int PORT = 12345;  // Port for the server to listen on
    private static final String USERS_FILE = "current_users.dat";  // File to store all current users
    private static final ArrayList<MessageViewerUser> currentUsers = loadUsersFromFile();  // Load all current users

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started. Listening for connections on port " + PORT);

            while (true) {
                // Wait for a new client connection
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                // Handle the new client in a separate thread by passing a Runnable (ClientHandler)
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();  // Start the thread to handle the client
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // This method loads the current users from the file when the server starts
    public static ArrayList<MessageViewerUser> loadUsersFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USERS_FILE))) {
            return (ArrayList<MessageViewerUser>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();  // Return an empty list if the file doesn't exist or an error occurs
        }
    }

    // This method saves the current users list to a file to persist data
    public static void saveUsersToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
            oos.writeObject(currentUsers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // This method authenticates a user by checking their username and password
    public static MessageViewerUser authenticateUser(String username, String password) {
        Scanner scanner = new Scanner(System.in);
        int attempts = 0;
        final int MAX_ATTEMPTS = 5;

        // Loop to allow 5 attempts for correct username and password
        while (attempts < MAX_ATTEMPTS) {
            // Search for the user in the list of current users
            for (MessageViewerUser user : currentUsers) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    // Successful authentication
                    System.out.println("Login successful!");
                    return user; // Return the authenticated user
                }
            }

            // If not authenticated, increment attempts and ask the user to re-enter credentials
            attempts++;
            if (attempts < MAX_ATTEMPTS) {
                System.out.print("Please enter your username: ");
                username = scanner.nextLine();  // Prompt for username again
                System.out.print("Please enter your password: ");
                password = scanner.nextLine();  // Prompt for password again
            }
        }

        // If the loop ends, the user has failed 5 attempts
        System.out.println("You have exceeded the maximum number of attempts. Please try again later.");
        return null;  // Return null if authentication fails after 5 attempts
    }

    // This class handles each individual client connection and implements Runnable
    static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private ObjectInputStream in;
        private ObjectOutputStream out;
        private MessageViewerUser currentUser;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
            try {
                this.in = new ObjectInputStream(clientSocket.getInputStream());
                this.out = new ObjectOutputStream(clientSocket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                // 1. Handle user login
                String username = (String) in.readObject();
                String password = (String) in.readObject();

                currentUser = authenticateUser(username, password);

                if (currentUser == null) {
                    out.writeObject("Invalid credentials, login failed.");
                    clientSocket.close();
                    return;
                }

                // 2. Send confirmation of login
                out.writeObject("Login successful. Welcome, " + currentUser.getName());

                // 3. Handle different client commands (sending messages, adding/removing friends, etc.)
                while (true) {
                    String command = (String) in.readObject();

                    if (command.equals("SEND_MESSAGE")) {
                        handleSendMessage();
                    } else if (command.equals("ADD_FRIEND")) {
                        handleAddFriend();
                    } else if (command.equals("REMOVE_FRIEND")) {
                        handleRemoveFriend();
                    } else if (command.equals("BLOCK_USER")) {
                        handleBlockUser();
                    } else if (command.equals("UNBLOCK_USER")) {
                        handleUnblockUser();
                    } else if (command.equals("VIEW_FRIENDS")) {
                        handleViewFriends();
                    } else if (command.equals("VIEW_BLOCKED")) {
                        handleViewBlocked();
                    } else if (command.equals("LOGOUT")) {
                        out.writeObject("Logging out...");
                        break;
                    } else {
                        out.writeObject("Invalid command.");
                    }
                }

                // Close the client socket when done
                clientSocket.close();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        // Handle sending a message from currentUser to another user
        private void handleSendMessage() throws IOException, ClassNotFoundException {
            String recipientUsername = (String) in.readObject();
            String content = (String) in.readObject();

            MessageViewerUser recipient = findUserByUsername(recipientUsername);
            if (recipient != null) {
                try {
                    currentUser.sendMessage(recipient, content);
                    out.writeObject("Message sent successfully.");
                } catch (BlockedUserException e) {
                    out.writeObject("Error: You are blocked by this user.");
                }
            } else {
                out.writeObject("User not found.");
            }
        }

        // Handle viewing friends list
        private void handleViewFriends() throws IOException {
            List<MessageViewerUser> friends = currentUser.getFriends();
            if (friends.isEmpty()) {
                out.writeObject("You have no friends.");
            } else {
                out.writeObject("Your friends:");
                for (MessageViewerUser friend : friends) {
                    out.writeObject(friend.getUsername());
                }
            }
        }

        // Handle viewing blocked users list
        private void handleViewBlocked() throws IOException {
            List<MessageViewerUser> blockedUsers = currentUser.getBlocked();
            if (blockedUsers.isEmpty()) {
                out.writeObject("You have not blocked any users.");
            } else {
                out.writeObject("Your blocked users:");
                for (MessageViewerUser blockedUser : blockedUsers) {
                    out.writeObject(blockedUser.getUsername());
                }
            }
        }

        // Handle adding a friend
        private void handleAddFriend() throws IOException, ClassNotFoundException {
            String friendUsername = (String) in.readObject();
            MessageViewerUser friend = findUserByUsername(friendUsername);
            if (friend != null) {
                currentUser.addFriend(friend);
                out.writeObject("Friend added successfully.");
            } else {
                out.writeObject("User not found.");
            }
        }

        // Handle removing a friend
        private void handleRemoveFriend() throws IOException, ClassNotFoundException {
            String friendUsername = (String) in.readObject();
            MessageViewerUser friend = findUserByUsername(friendUsername);
            if (friend != null) {
                currentUser.removeFriend(friend);
                out.writeObject("Friend removed successfully.");
            } else {
                out.writeObject("User not found.");
            }
        }

        // Handle blocking a user
        private void handleBlockUser() throws IOException, ClassNotFoundException {
            String usernameToBlock = (String) in.readObject();
            MessageViewerUser userToBlock = findUserByUsername(usernameToBlock);
            if (userToBlock != null) {
                try {
                    currentUser.blockUser(userToBlock);
                    out.writeObject("User blocked successfully.");
                } catch (BlockedUserException e) {
                    out.writeObject("Error: This user is already blocked.");
                }
            } else {
                out.writeObject("User not found.");
            }
        }


        // Handle unblocking a user
        private void handleUnblockUser() throws IOException, ClassNotFoundException {
            String usernameToUnblock = (String) in.readObject();
            MessageViewerUser userToUnblock = findUserByUsername(usernameToUnblock);
            if (userToUnblock != null) {
                try {
                    currentUser.unblockUser(userToUnblock);
                    out.writeObject("User unblocked successfully.");
                } catch (BlockedUserException e) {
                    out.writeObject("Error: This user is not blocked.");
                }
            } else {
                out.writeObject("User not found.");
            }
        }



        // Helper method to find a user by their username
        private MessageViewerUser findUserByUsername(String username) {
            for (MessageViewerUser user : currentUsers) {
                if (user.getUsername().equals(username)) {
                    return user;
                }
            }
            return null;
        }
    }


}
