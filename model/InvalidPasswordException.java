package model;

/**
 * model.InvalidPasswordException - A custom exception thrown when an invalid password is provided during a user authentication attempt.
 *
 * @author L10-Team 1
 * @version November 3, 2024
 */
public class InvalidPasswordException extends Exception{
    public InvalidPasswordException(String message) {
        super(message);
    }
}
