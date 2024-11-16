/**
 * The ClientGUI class provides a console-based interface for interacting with a 
 * messaging server. Users can log in, create accounts, and perform various actions 
 * such as searching messages, managing friends, and signing out. 
 *
 * This class is part of a client-server messaging application and is intended to 
 * send user inputs to the server for processing. The implementation includes menus 
 * for navigation and placeholders for server communication.
 *
 * Features include:
 * <ul>
 *   <li>Log In functionality</li>
 *   <li>Account creation</li>
 *   <li>Friend management (add, remove, block)</li>
 *   <li>Message searching</li>
 *   <li>Sign out functionality</li>
 * </ul>
 * </p>
 *
 * @author L10-Team 1
 * @version November 16, 2024
 */
import java.util.*;
public class ClientGUI implements ClientInterface {
	
	public static int startScreen() {
		Scanner keyboard = new Scanner(System.in);
		boolean isValid;
		int choice = -1;
		do {
			isValid = true;
			System.out.println("1) Log In \n2) Create An Account \n3) Exit");
			try {
				choice = Integer.parseInt(keyboard.nextLine());
			}
			catch(NumberFormatException e) {
				System.out.println("Please enter a valid choice");
				isValid = false;
			}
			if(choice < 1 || choice > 3) {
				System.out.println("Please enter a valid choice");
				isValid = false;
			}
		} while(!isValid);
		return choice; 
	}
	
	public static int homeScreen() {
		Scanner keyboard = new Scanner(System.in);
		boolean isValid;
		int choice = -1;
		do {
			isValid = true;
			System.out.println("1) Search Messages \n2) Add Friends \n3) Remove Friends \n 4) Block Friends \n5) Sign Out");
			try {
				choice = Integer.parseInt(keyboard.nextLine());
			}
			catch(NumberFormatException e) {
				System.out.println("Please enter a valid choice");
				isValid = false;
			}
			if(choice < 1 || choice > 5) {
				System.out.println("Please enter a valid choice");
				isValid = false;
			}
		} while(!isValid);
		return choice; 
	}
	
	public static void main(String[] args) {
		
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("Welcome to Message Viewer! \nChoose one of the following options to continue!");
		boolean homeAgain = true;
		while(homeAgain) {
			int homeScreenChoice = startScreen();
			switch (homeScreenChoice) {
				case 1:
					System.out.println("Enter your username: ");
					String username = keyboard.nextLine();
					System.out.println("Enter your password: ");
					String password = keyboard.nextLine();
					//pass the username and password into the server to check and see if 
					//credentials are correct, if not return an error with "username or password
					//incorrect"
					System.out.println("Welcome " + username + "! \nChoose one of the following options to continue!");
					boolean userAgain = true;
					while(userAgain) {
						int userChoice = homeScreen();
						switch(userChoice) {
							case 1:
								System.out.println("Enter search word: ");
								String searchWord = keyboard.nextLine();
								//pass the searchWord into the server to use the search functionality,
								//should show past messages and allow user to send more messages
								break;
							case 2:
								System.out.println("Enter friend name: ");
								String addName = keyboard.nextLine();
								//pass the user name into the server to use the add friend functionality,
								//if friend does not exist return UserNotFound Exception
								break;
							case 3:
								System.out.println("Enter friend name: ");
								String removeName = keyboard.nextLine();
								//pass the user name into the server to use the remove friend functionality,
								//if friend does not exist return UserNotFound Exception
								break;
							case 4:
								System.out.println("Enter friend name: ");
								String blockName = keyboard.nextLine();
								//pass the user name into the server to use the block friend functionality,
								//if friend does not exist return UserNotFound Exception
								break;
							case 5:
								System.out.println("Thank you for using MessageViewer! Bye " + username + "!");
								userAgain = false;
								break;
						}
					}
					break;
				case 2:
					System.out.println("Enter a username: ");
					String newUsername = keyboard.nextLine();
					System.out.println("Enter a password: ");
					String newPassword = keyboard.nextLine();
					//pass the username and password into the server to check and see if 
					//username and password are valid, if not return InvalidPassword or InvaldUsername
					//exceptions
					break;
				case 3:
					System.out.println("Thank you for using Message Viewer!");
					homeAgain = false;
					break;
			}
		}
		
	}
}
