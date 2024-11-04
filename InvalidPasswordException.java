/**
 * InvalidPasswordException - A custom exception thrown when an invalid password is provided during a user authentication attempt.
 *
 * @see Exception
 * @version November 3, 2024
 */
public class InvalidPasswordException extends Exception{
    public InvalidPasswordException(String message) {
        super(message);
    }
}
