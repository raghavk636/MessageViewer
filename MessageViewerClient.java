import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MessageViewerClient {
    private static final String SERVER_ADDRESS = "localhost"; // Server address
    private static final int SERVER_PORT = 12345;             // Server port

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Welcome to MessageViewer!");

            // Authenticate the user
            boolean authenticated = false;
            while (!authenticated) {
                System.out.println("\nMenu:");
                System.out.println("1. Log in");
                System.out.println("2. Create Account");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1": // Log in
                        System.out.print("Enter your username: ");
                        String username = scanner.nextLine();
                        System.out.print("Enter your password: ");
                        String password = scanner.nextLine();

                        out.writeObject(username);
                        out.writeObject(password);

                        String loginResponse = (String) in.readObject();
                        System.out.println(loginResponse);

                        if (loginResponse.startsWith("Login successful")) {
                            authenticated = true;
                        }
                        break;

                    case "2":  // User chooses to create an account
                        out.writeObject("CREATE_ACCOUNT"); // Inform the server about the action

                        // Handle the account creation prompts and responses
                        System.out.print("Enter a name:"); // Prompt: Enter a name
                        String name = scanner.nextLine();
                        out.writeObject(name); // Send name to server

                        System.out.print("Enter a username:"); // Prompt: Enter a new username
                        String usernameForCreation = scanner.nextLine();
                        out.writeObject(usernameForCreation); // Send username to server

                        System.out.print("Enter a password:"); // Prompt: Enter a new password
                        String passwordForCreation = scanner.nextLine();
                        out.writeObject(passwordForCreation); // Send password to server

                        // Receive and display the server's response
                        String creationResponse = (String) in.readObject();
                        System.out.println(creationResponse);
                        break;

                    case "3": // Exit
                        System.out.println("Exiting...");
                        return;

                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }

            // After login, display available commands
            while (true) {
                System.out.println("\nAvailable Commands:");
                System.out.println("1. Send Message");
                System.out.println("2. Add Friend");
                System.out.println("3. Remove Friend");
                System.out.println("4. Block User");
                System.out.println("5. Unblock User");
                System.out.println("6. View Friends");
                System.out.println("7. View Blocked Users");
                System.out.println("8. Logout");
                System.out.print("Enter your choice: ");
                String command = scanner.nextLine();

                switch (command) {
                    case "1": // Send Message
                        System.out.print("Enter recipient's username: ");
                        String recipient = scanner.nextLine();
                        System.out.print("Enter your message: ");
                        String message = scanner.nextLine();
                        out.writeObject("SEND_MESSAGE");
                        out.writeObject(recipient);
                        out.writeObject(message);
                        break;

                    case "2": // Add Friend
                        System.out.print("Enter the username of the friend to add: ");
                        String friendToAdd = scanner.nextLine();
                        out.writeObject("ADD_FRIEND");
                        out.writeObject(friendToAdd);
                        break;

                    case "3": // Remove Friend
                        System.out.print("Enter the username of the friend to remove: ");
                        String friendToRemove = scanner.nextLine();
                        out.writeObject("REMOVE_FRIEND");
                        out.writeObject(friendToRemove);
                        break;

                    case "4": // Block User
                        System.out.print("Enter the username of the user to block: ");
                        String userToBlock = scanner.nextLine();
                        out.writeObject("BLOCK_USER");
                        out.writeObject(userToBlock);
                        break;

                    case "5": // Unblock User
                        System.out.print("Enter the username of the user to unblock: ");
                        String userToUnblock = scanner.nextLine();
                        out.writeObject("UNBLOCK_USER");
                        out.writeObject(userToUnblock);
                        break;

                    case "6": // View Friends
                        out.writeObject("VIEW_FRIENDS");
                        break;

                    case "7": // View Blocked Users
                        out.writeObject("VIEW_BLOCKED");
                        break;

                    case "8": // Logout
                        out.writeObject("LOGOUT");
                        System.out.println("You have been logged out. Goodbye!");
                        return;

                    default:
                        System.out.println("Invalid command. Please try again.");
                }

                // Read and display the server's response
                String serverResponse = (String) in.readObject();
                System.out.println(serverResponse);
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
