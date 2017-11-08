package init;

import java.io.BufferedReader;
import java.io.IOException;

import javax.swing.JTextArea;

public class ReadingThread extends Thread {
	BufferedReader in;
	JTextArea ta;
	boolean GUI_Mode = false, isClient = false;
	
	// Constructor for console
	public ReadingThread(BufferedReader in) {
		this.in = in;
	}
	
	// Constructor for GUI
	public ReadingThread(BufferedReader in, JTextArea ta) {
		this.in = in;
		this.ta = ta;
		GUI_Mode = true;
	}	
	
	// Constructor for console
	public ReadingThread(BufferedReader in, boolean isClient) {
		this.in = in;
		this.isClient = isClient;
	}	
	
	// Constructor for GUI
	public ReadingThread(BufferedReader in, JTextArea ta, boolean isClient) {
		this.in = in;
		this.ta = ta;
		GUI_Mode = true;
		this.isClient = isClient;
	}	
	
	public void run() {
		String input;	
		try {
			while ((input = in.readLine()) != null) {
				if (GUI_Mode) {
					displayMessage(ta, input);
				}
				else
					if (isClient)
						Logging.writeToClientChatLog("Server: " + input);
					else
						Logging.writeToServerChatLog("Client: " + input);
			}
		} catch (IOException e) {
			if (GUI_Mode)
				if (isClient)
					Logging.displayClientErrorMessage("Failed to read from server!");
				else
					Logging.displayServerErrorMessage("Failed to read from client!");
			else
				if (isClient)
					Logging.writeToClientLog("Failed to read from server!", true);
				else
					Logging.writeToServerLog("Failed to read from client!", true);
		}
	}	
	
	// Displays message in GUI
	private void displayMessage(JTextArea ta, String msg) {
		// Format message
		String header;
		if (isClient)
			header = "Server: ";
		else
			header = "Client: ";
		String newMsg = header + msg;
		// Append to main text area
		ta.append(newMsg + "\n");
		// Log message
		if (isClient)
			Logging.writeToClientChatLog(newMsg);
		else
			Logging.writeToServerChatLog(newMsg);		
	}
}