package init;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

public class Logging {
	// File path constants
	private static Filepaths fp = new Filepaths();
	
	// Writes message to log as well as console
	private static void writeToLog(String logInfo, Boolean printToConsole, File logFile) {
		try {
			// Initialize log file writer
			FileWriter logFileWriter = new FileWriter(logFile, true/*Appends to log file.*/);
			// Get current time and date
			Date date = new Date();
			// Format time and date
			SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a");
			// Format message into string
			String formattedMessage = ft.format(date) + ": " + logInfo + "\n";
			// Write to log file
			logFileWriter.write(formattedMessage);
			// Print to console
			if (printToConsole) {
				System.out.print(formattedMessage);			
			}
			// Close log file writer
			logFileWriter.close();
		} catch (IOException e) {
			System.out.println("Could not write to log file!");
		}
	}
	
	// Write to client log
	public static void writeToClientLog(String logInfo, Boolean printToConsole) {
		writeToLog(logInfo, printToConsole, new File(fp.CLIENT_LOG));
	}
	
	// Write to server log
	public static void writeToServerLog(String logInfo, Boolean printToConsole) {
		writeToLog(logInfo, printToConsole, new File(fp.SERVER_LOG));
	}
	
	// Write to client chat log
	public static void writeToClientChatLog(String logInfo) {
		writeToLog(logInfo, true/*Print to console*/, new File(fp.CLIENT_CHAT_LOG));	
	}
	
	// Write to server chat log
	public static void writeToServerChatLog(String logInfo) {
		writeToLog(logInfo, true/*Print to console*/, new File(fp.SERVER_CHAT_LOG));	
	}
	
	// Display Client GUI error message
	public static void displayClientErrorMessage(String errormsg) {
		Logging.writeToClientLog(errormsg, true);
		JOptionPane.showMessageDialog(null, errormsg);
	}
	
	// Display Server GUI error message
	public static void displayServerErrorMessage(String errormsg) {
		Logging.writeToServerLog(errormsg, true);
		JOptionPane.showMessageDialog(null, errormsg);
	}	
}
