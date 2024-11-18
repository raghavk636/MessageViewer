/**
 * The PortInformationInterface defines constants for the server's connection details,
 * including the address and port number.
 * 
 * These constants are used by clients and servers to establish a socket connection.
 * 
 * Usage of this interface ensures consistency in the connection details across different
 * components of the application.
 * 
 * @author L10- Team 1
 * @version November 17 2024
 */
public interface PortInformationInterface {
	public static final String SERVER_ADDRESS = "localhost"; // Server address
    public static final int SERVER_PORT = 12345;			 // Server port
}
