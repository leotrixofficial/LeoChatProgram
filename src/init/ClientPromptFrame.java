package init;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ClientPromptFrame extends JFrame {
	public ClientPromptFrame() {
		Logging.writeToClientLog("Prompt frame has been initialized!", true);
		
		// Initialize connection details
		ConnectionDetails details = new ConnectionDetails();
		details.port = 9006;

		// Initialize components for frame
		JTextField addressTextField = new JTextField("localhost");
		JButton connectButton = new JButton();
		Logging.writeToClientLog("Components for prompt frame have been initialized.", true);
		
		// connectButton properties
		connectButton.setText("Connect");
		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Logging.writeToClientLog("Connect button clicked!", true);
				details.serverAddress = addressTextField.getText();
				try {
					new ChatFrame(ClientTools.initializeSocket(details, true/*GUI mode*/), true/*client mode*/);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				dispose();
				Logging.writeToClientLog("Prompt frame closed.", true);
			}
		});
		
		// Add components to frame
		add(addressTextField);
		add(connectButton);
		
		// PromptFrame properties
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		setLayout(new GridLayout(1,2));
		setSize(300,50);
		setTitle("Client Prompt");
		setVisible(true);
	}
}
