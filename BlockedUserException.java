/**
 * BlockedUserException - A custom exception thrown when an action is attempted on a user who is blocked.
 *  
 * @author L10-Team 1
 * @version November 3, 2024
 */
public class BlockedUserException extends Exception{
    public BlockedUserException(String message) {
        super(message);
    }
}
