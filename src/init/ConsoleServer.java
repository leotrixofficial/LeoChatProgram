package init;

import java.io.*;
import java.net.*;
import java.util.Scanner;

class serverPort {
	// Port number for server to listen on
	public static int port = 9006;
}

public class ConsoleServer {
	// Handles arguments passed into program
	private static void argumentHandling(String args[]) {
		// If there is an argument, isn't null value and is a number...
		if (args.length > 0) {
			if (args[0] != null && args[0].matches("^-?\\d+$"/*This regex checks if the argument passed is a number.*/)) {
				// ...assign to server's listening port.
				serverPort.port = Integer.parseInt(args[0]);
				Logging.writeToServerLog("Server port assigned to " + serverPort.port, true);
			} else if (args[0].equals("-h")) {
				printHelp();
			}
		}
	}
	
	// Prints help for using program's commands
	private static void printHelp() {
		System.out.println("LeoChatProgram Console Server - Help\n ");
		System.out.println("ConsoleServer PORT_NUMBER - Launch server on specified port");
		System.out.println("ConsoleServer -h - Help");
	}
	
	public static void main(String args[]) throws IOException {
		// Handle arguments passed on execution of program
		argumentHandling(args);
		
		// Instantiate scanner on system input
		Scanner stdIn = new Scanner(System.in);
		Logging.writeToServerLog("Scanner instantied with standard input.", false);	
		
		// Initialize server socket
		ServerSocket listener = ServerTools.initializeServerSocket();
		
		// Initialize client socket
		Socket socket = ServerTools.initializeClientSocket(listener);
		
		// Instantiate a BufferedReader for receiving string from the client
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		Logging.writeToServerLog("BufferedReader instantied with socket's input stream.", false);
		
		// Instantiate a PrintWriter for sending string data to the client through the socket's output stream
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		Logging.writeToServerLog("PrintWriter instantied with socket's output stream.", false);
		
		// Send connection confirmation message to client
		ServerTools.sendMessageToClient(out, "Client connected!");
		
		// Start thread for reading messages from client
		new ReadingThread(in).start();
		Logging.writeToServerLog("Server reading thread started!", false);		
		
		// Start thread for sending messages to client
		new SendingThread(stdIn, out).start();
		Logging.writeToServerLog("Server sending thread started!", false);
	}
}
