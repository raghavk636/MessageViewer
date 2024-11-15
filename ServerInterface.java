/**
 * ServerInterface - An interface defining the methods used in the server class.
 *
 * 
 * @author L10-Team 1
 * @version November 14, 2024
 */
import java.util.ArrayList;

public interface ServerInterface {
// METHODS
    static void saveUsersToFile() // Save users to file
    {}

    static ArrayList<MessageViewerUser> loadUsersFromFile()  // Load users from file
    {
        return null;
    }

    static MessageViewerUser authenticateUser(String username, String password)  // Authenticate user
    {
        return null;
    }

}
