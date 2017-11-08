package init;

import java.io.PrintWriter;
import java.util.Scanner;

// Thread for sending user input through output stream
// This thread is only used in the console versions of the client and server
public class SendingThread extends Thread {
	Scanner stdIn;
	PrintWriter out;
	boolean isClient = false;
	public SendingThread(Scanner stdIn, PrintWriter out) {
		this.stdIn = stdIn;
		this.out = out;
	}
	public SendingThread(Scanner stdIn, PrintWriter out, boolean isClient) {
		this.stdIn = stdIn;
		this.out = out;
		this.isClient = isClient;
	}
	public void run() {
		// Keep looping until user enters command "exit"
		String userInput;
		// Run a loop that gets the next line of string data from the user
		while (!(userInput = stdIn.nextLine()).equalsIgnoreCase("/exit")) {
			if (userInput != null) {
				// Send user input to client
				out.write(userInput + "\n");
				// Flush output stream
				out.flush();
				if (isClient)
					Logging.writeToClientChatLog("Client: " + userInput);
				else
					Logging.writeToServerChatLog("Server: " + userInput);
			}
		}	
		if (isClient)
			Logging.writeToClientLog("Client closed.", true);
		else
			Logging.writeToServerLog("Server closed.", true);
		System.exit(2);
	}
}
