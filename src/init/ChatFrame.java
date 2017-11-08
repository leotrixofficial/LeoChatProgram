package init;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class ChatFrame extends JFrame {
	private boolean isClient = false;
	public ChatFrame(Socket socket, boolean isClient) throws IOException {
		this.isClient = isClient;
		final String newline = "\n";
		if (isClient)
			Logging.writeToClientLog("Chat frame has been initialized!", true);
		else
			Logging.writeToServerLog("Chat frame has been initialized!", true);
		
		// Instantiate a BufferedReader for receiving string from the server
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		Logging.writeToClientLog("BufferedReader instantied with socket's input stream.", false);
		
		// Instantiate a PrintWriter for sending string data to the server through the socket's output stream
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		Logging.writeToClientLog("PrintWriter instantied with socket's output stream.", false);			
		
		// Initialize text area and new message area
		JTextArea ta = new JTextArea("Welcome to Leo's Chat Program!" + newline),
				newMessageArea = new JTextArea();
		ta.setEditable(false);
		
		// Initialize chat panel
		JPanel chatPanel = new JPanel(new GridLayout(1,1));
		chatPanel.add(new JScrollPane(ta));
		
		// Initialize send button with action listener
		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Logging.writeToClientLog("Send button clicked!", true);
				sendMessage(ta, newMessageArea, out);
			}
		});
		
		// Start new thread for reading the data stream from server
		new ReadingThread(in, ta, isClient).start();
		if (isClient)
			Logging.writeToClientLog("GUI Client reading thread started!", false);
		else
			Logging.writeToServerLog("GUI Server reading thread started!", false);
				
		// Add components to frame
		add(chatPanel);
		add(newMessageArea);
		add(sendButton);
		
		// PromptFrame properties
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		setLayout(new GridLayout(3,1));
		setSize(400,400);
		if (isClient)
			setTitle("Client Chat Program");
		else
			setTitle("Server Chat Program");
		setVisible(true);
	}
	
	// Logs message to chat log then sends it out to server
	private void sendMessage(JTextArea ta, JTextArea newMsgArea, PrintWriter out) {
		// Get new message from new message text area
		String newMsg = newMsgArea.getText();
		// Clear text area
		newMsgArea.setText("");
		// Make header for message
		String header;
		if (isClient)
			header = "Client: ";
		else
			header = "Server: ";
		// Append to main text area
		ta.append(header + newMsg + "\n");
		// Log message
		Logging.writeToClientChatLog(header + newMsg);
		// Send message to server
		out.println(newMsg);
	}
}
