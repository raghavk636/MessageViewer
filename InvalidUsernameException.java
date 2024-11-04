/**
 * InvalidUsernameException - A custom exception thrown when an invalid username is provided during user registration
 *
 * @author L-10 Team-1 
 * @version November 3, 2024
 */
public class InvalidUsernameException extends Exception{
    public InvalidUsernameException(String message) {
        super(message);
    }
}
