import java.io.*;
import java.net.*;
import java.util.Scanner;

import controller.Controller;
import view.*;
/**
 * The MessageViewerClient class provides a command-line interface
 * for a user to interact with a server-based messaging system. 
 * Users can log in, create accounts, send messages, manage friends, and block or unblock users.
 * 
 * This client communicates with a server using sockets and serializes data via 
 * ObjectOutputStream and ObjectInputStream.
 * 
 *
 * 
 * @author L10- Team 1
 * @version November 17, 2024
 */
public class MessageViewerClient implements PortInformationInterface{

    public static void main(String[] args){
        main_UI(args);
//       main_console(args);
    }
    public static void main_UI(String[] args){
        //controller will establish the connection and communicate with server
        Controller controller = new Controller();
        new WelcomeUI(controller);
    }
    public static void main_console(String[] args) {
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

                        out.writeObject("LOGIN_ACCOUNT");
                        out.writeObject(username);
                        out.writeObject(password);

                        String loginResponse = (String) in.readObject();
                        System.out.println(loginResponse);
                        menuDisplay(scanner,in,out);
                        break;


                    case "2":  // User chooses to create an account
                        System.out.print("Enter a name:"); // Prompt: Enter a name
                        String name = scanner.nextLine();

                        System.out.print("Enter a username:"); // Prompt: Enter a new username
                        String usernameForCreation = scanner.nextLine();

                        System.out.print("Enter a password:"); // Prompt: Enter a new password
                        String passwordForCreation = scanner.nextLine();

                        out.writeObject("CREATE_ACCOUNT"); // Inform the server about the action

                        // Handle the account creation prompts and responses

                        out.writeObject(name); // Send name to server
                        out.writeObject(usernameForCreation); // Send username to server
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





        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void menuDisplay(Scanner scanner, ObjectInputStream in, ObjectOutputStream out)
            throws IOException {

        while (true) {
            System.out.println("\nAvailable Commands:");
            System.out.println("1. Send model.Message");
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
                case "1": // Send model.Message
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
            String serverResponse = null;
            try {
                serverResponse = (String) in.readObject();
            } catch (Exception e) {

            }
            System.out.println(serverResponse);
        }

    }
}
