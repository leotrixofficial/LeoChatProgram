package init;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JOptionPane;

public class ClientTools extends ConnectionTools {
	// Returns an initialized socket
	public static Socket initializeSocket(ConnectionDetails details) throws IOException {
		Socket socket = null;
		try {
			socket = new Socket(details.serverAddress, details.port);
			Logging.writeToClientLog("Initialized socket.", false);
			Logging.writeToClientLog("Connecting to server: " + details.serverAddress, true);
		} catch (SocketException e) {
			String errormsg = "Server: " + details.serverAddress + ":" + details.port + " was not found! Client terminated.";
			Logging.writeToClientLog(errormsg, true);
			if (This_GUI_Mode)
				JOptionPane.showMessageDialog(null, errormsg);
			// Close program if cannot find the server
			System.exit(1);
		}
		return socket;
	}
	
	// Initialize socket in GUI_Mode
	public static Socket initializeSocket(ConnectionDetails details, Boolean GUI_Mode) throws IOException {
		This_GUI_Mode = GUI_Mode;
		return initializeSocket(details);
	}
}
