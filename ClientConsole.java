
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import client.ChatClient;
import common.ChatIF;

/**
 * This class constructs the UI for a chat client. It implements the chat
 * interface in order to activate the display() method. Warning: Some of the
 * code here is cloned in ServerConsole
 *
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @version July 2000
 */

public class ClientConsole implements ChatIF {
	// Class variables *************************************************

	/**
	 * The default port to connect on.
	 */
	final public static int DEFAULT_PORT = 5555;

	// Instance variables **********************************************

	/**
	 * The instance of the client that created this ConsoleChat.
	 */
	ChatClient client;

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the ClientConsole UI.
	 *
	 * @param host The host to connect to.
	 * @param port The port to connect on.
	 * 
	 *             takes a username string for E7-a to detect client username and
	 *             loginId
	 */
	public ClientConsole(String username, String host, int port) {
		try {
			client = new ChatClient(username, host, port, this);
		} catch (IOException exception) {
			System.out.println("Error: Can't setup connection!" + " Terminating client.");
			System.exit(1);
		}
	}

	// Instance methods ************************************************

	/**
	 * This method waits for input from the console. Once it is received, it sends
	 * it to the client's message handler.
	 */
	public void accept() {
		try {
			BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
			String message;

			while (true) {
				message = fromConsole.readLine();
				client.handleMessageFromClientUI(message);
			}
		} catch (Exception ex) {
			System.out.println("Unexpected error while reading from console!");
		}
	}

	/**
	 * This method overrides the method in the ChatIF interface. It displays a
	 * message onto the screen.
	 *
	 * @param message The string to be displayed.
	 */
	public void display(String message) {
		System.out.println("> " + message);
	}

	// Class methods ***************************************************

	/**
	 * This method is responsible for the creation of the Client UI.
	 *
	 * @param args[0] The host to connect to.
	 */
	public static void main(String[] args) {
		String host = "";

		int port;
		String username;

		/*
		 * Modified for E7-b Require a login ID
		 */
		try {
			username = args[0];
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("ERROR - No login ID specified.  Connection aborted.");
			System.exit(1);
			return;
		}

		try {
			host = args[1];
		} catch (ArrayIndexOutOfBoundsException e) {
			host = "localhost";
		}

		// **** Changed for E5 b to let users input the desired port number
		Scanner scan = new Scanner(System.in);
		System.out.println(
				"Please Enter your  port number that SHOULD MATCH WITH THE SERVER PORT NUMBER U ALREADY INSERTED!!!!");
		port = scan.nextInt(); // The port number
		if (port < 4092) {
			System.out.println("Port Number indicated is NOT Dynamic!!!");
			System.exit(0);
		}

		try {
			host = args[0];
		} catch (ArrayIndexOutOfBoundsException e) {
			host = "localhost";

		}
		ClientConsole chat = new ClientConsole(username, host, port);
		System.out.println("Perfect! You can now input your text");
		chat.accept(); // Wait for console data
		if (!chat.client.isConnected())
			scan.close();
	}
}
//End of ConsoleChat class
