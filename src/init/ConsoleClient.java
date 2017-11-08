package init;

import java.io.*;
import java.net.*;
import java.util.*;

public class ConsoleClient {
	// Gets server address from user
	private static String getServerAddress(Scanner inputScanner) {
		// Server's address
		String serverAddress;
		// Prompt for server address
		Logging.writeToClientLog("User prompted for server address.", false);
		System.out.print("Enter address: ");
		// Open scanner for system input
		Logging.writeToClientLog("Opening scanner for system input.", false);
		// Read next line
		serverAddress = inputScanner.nextLine();
		// If server address provided is empty or null, make it "localhost" by default
		if (serverAddress.trim().isEmpty() || serverAddress == null) {
			Logging.writeToClientLog("No server address provided. Server address set to default: localhost", false);
			serverAddress = "localhost";
		} else {
			Logging.writeToClientLog("Server address assigned to " + serverAddress + ".", false);
		}
		// Close input scanner
		Logging.writeToClientLog("Closing system input scanner.", false);
		// Return server address
		return serverAddress;
	}
	
	// Reads through the arguments provided and assigns the necessary server address and port
	private static void readConnectionArgs(String[] args, ConnectionDetails details, Scanner sc) {
		Logging.writeToClientLog("Reading arguments.", false);
		// If there are any arguments provided
		if (args.length > 0) {
			Logging.writeToClientLog(args.length + " argument(s) provided.", false);
			// Check through every argument provided
			for (int i = 0; i < args.length; i++) {
				// Check if "-h" flag has been used
				if (args[i].equals("-h")) {
					// Print help for using program's commands
					printHelp();
					return;
				}
			}
			// If there's an argument providing the address of the server
			if (args[0] != null) {
				// Assign server address
				details.serverAddress = args[0];
				Logging.writeToClientLog("Server address provided in arguments: " + details.serverAddress, false);
				// If there's an argument providing a specific port to connect to
				if (args.length > 1 && args[1] != null && args[1].matches("^-?\\d+$"/*This regex checks if the argument passed is a number.*/)) {	
					// Assign server port
					details.port = Integer.parseInt(args[1]);
					Logging.writeToClientLog("Port number provided in arguments: " + details.port , false);
				}
			}
		} else {
			Logging.writeToClientLog("No arguments provided. Prompting user for server address.", false);
			// Get server address from user
			details.serverAddress = getServerAddress(sc);
			// Assign port to 9006 by default
			details.port = 9006;
		}	
	}

	// TODO: Prints help for using program's commands
	private static void printHelp() {
		Logging.writeToClientLog("Help for using program printed to console.", false);
		System.exit(0);
	}
	
	public static void main(String args[]) throws IOException {
		// Instantiate class for connection to server details (address and port)
		ConnectionDetails details = new ConnectionDetails();
		
		// Instantiate scanner on system input
		Scanner stdIn = new Scanner(System.in);
		Logging.writeToClientLog("Scanner instantied with standard input.", false);		
		
		// Finds the connection details through provided arguments or will prompt for user input
		readConnectionArgs(args, details, stdIn);
		
		//Initialize socket
		Socket socket = ClientTools.initializeSocket(details);
		
		// Instantiate a BufferedReader for receiving string from the client
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		Logging.writeToClientLog("BufferedReader instantied with socket's input stream.", false);
		
		// Instantiate a PrintWriter for sending string data to the client through the socket's output stream
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		Logging.writeToClientLog("PrintWriter instantied with socket's output stream.", false);	
		
		// Start new thread for reading the data stream from server
		new ReadingThread(in, true/*client mode*/).start();
		Logging.writeToClientLog("Client reading thread started!", false);		
		
		// Start new thread for sending messages to the server
		new SendingThread(stdIn, out, true/*client mode*/).start();
		Logging.writeToClientLog("Client sending thread started!", false);		
	}
}
