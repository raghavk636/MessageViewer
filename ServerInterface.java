import java.util.ArrayList;

public interface ServerInterface {

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
