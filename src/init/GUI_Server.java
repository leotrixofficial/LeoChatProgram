package init;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GUI_Server {
	public static void main(String[] args) throws IOException {
		// Initialize server socket
		ServerSocket listener = ServerTools.initializeServerSocket(true/*GUI mode*/);
		// Initialize client socket
		Socket socket = ServerTools.initializeClientSocket(listener, true/*GUI mode*/);
		// Open new chat frame for server
		new ChatFrame(socket, false/*server mode*/);
	}
}
