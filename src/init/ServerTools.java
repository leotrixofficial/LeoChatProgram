package init;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTools extends ConnectionTools {
	// Initializes server socket
	public static ServerSocket initializeServerSocket() {
		ServerSocket listener = null;
		Logging.writeToServerLog("Listener socket initialized.", false);
		try {
			// Try to instantiate server socket class with given port number
			listener = new ServerSocket(/*PORT*/serverPort.port);
			Logging.writeToServerLog("Server is running!", true);
		} catch (BindException e) {
			// If there is already a server running on the given port, BindException is thrown
			if (This_GUI_Mode)
				Logging.displayServerErrorMessage("Server address/port is already in use!");
			else
				Logging.writeToServerLog("Server address/port is already in use!", true);
			// Close the server program
			System.exit(0);
		} catch (IOException e) {
			if (This_GUI_Mode)
				Logging.displayServerErrorMessage("Server IO Error!");
			else
				Logging.writeToServerLog("Server IO Error!", true);
			System.exit(3);
		}
		return listener;
	}
	
	// Initializes client socket
	public static Socket initializeClientSocket(ServerSocket listener) {
		Socket socket = null;
		Logging.writeToServerLog("Client socket initialized.", true);
		// Wait for client socket connection to accept
		Logging.writeToServerLog("Waiting for client to accept connection...", true);
		try {
			socket = listener.accept();
			Logging.writeToServerLog("Client connection accepted!", true);
		} catch (IOException e) {
			Logging.writeToServerLog("Failed to connect to client!", true);
			System.exit(1);
		}
		return socket;
	}
	
	// Sends singular message to client
	public static void sendMessageToClient(PrintWriter out, String message) {
		out.println(message);
		out.flush();
		Logging.writeToServerChatLog("Server: " + message);
	}
	
	// Initializes server socket in GUI Mode
	public static ServerSocket initializeServerSocket(boolean GUI_Mode) {
		This_GUI_Mode = GUI_Mode;
		return initializeServerSocket();
	}
	
	// Initializes client socket in GUI Mode
	public static Socket initializeClientSocket(ServerSocket listener, boolean GUI_Mode) {
		This_GUI_Mode = GUI_Mode;
		return initializeClientSocket(listener);
	}	
}
