package com.xmpp.imp;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Type;
import org.jivesoftware.smackx.ChatState;
import org.jivesoftware.smackx.ChatStateListener;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.FormField;
import org.jivesoftware.smackx.filetransfer.FileTransfer.Status;
import org.jivesoftware.smackx.filetransfer.FileTransferListener;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.FileTransferRequest;
import org.jivesoftware.smackx.filetransfer.IncomingFileTransfer;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.packet.VCard;

public class XmppClient {

	// Global variables
	private String server;
	private int port;

	private ConnectionConfiguration config;
	private XMPPConnection connection;

	private ChatManager chatManager;
	private MessageListener messageListener;

	/**
	 * Sets the server and port global variables
	 * 
	 * @param server
	 * @param port
	 */
	public XmppClient(String server, int port) {
		this.server = server;
		this.port = port;
	}

	/**
	 * Initialize the connection with the server and sets the listener for the chat
	 * 
	 * @throws XMPPException
	 */

	public void init() throws XMPPException {
		System.out.println(String.format("Iniciando conexion al server %1$s port %2$d", server, port));

		// Sets configuration of the server
		config = new ConnectionConfiguration(server, port);

		// Generate the connection with the server
		connection = new XMPPConnection(config);
		connection.connect();

		System.out.println("conectado: " + connection.isConnected());

		/*
		 * Start chatManager to create later a new chat, start messageListener to keep
		 * listening while the conversation takes place
		 */
		chatManager = connection.getChatManager();
		messageListener = new MyMessageListener();

		/**
		 * Create chat listener to be updated of any new message in the conversation
		 */
		connection.getChatManager().addChatListener(new ChatManagerListener() {

			/**
			 * When chat is created adds the listener to it
			 */
			public void chatCreated(final Chat arg0, final boolean arg1) {
				// TODO Auto-generated method stub

				arg0.addMessageListener(new MessageListener() {

					// Process the messages that come to the user
					public void processMessage(Chat arg0, Message arg1) {
						// TODO Auto-generated method stub

						System.out.println("is typing......");
						String from = arg1.getFrom();
						String body = arg1.getBody();
						System.out.println(String.format("Recibiendo mensaje '%1$s' from %2$s", body, from));
					}
				});
			}
		});

	}

	/**
	 * Performs the login with the user and password given
	 * 
	 * @param username
	 * @param password
	 * @throws XMPPException
	 */
	public void performLogin(String username, String password) throws XMPPException {
		// Validate that the connection with the server is established and is not null
		// and then performs the login
		if (connection != null && connection.isConnected()) {
			connection.login(username, password);
			System.out.println("Conectado");
		}
	}

	/**
	 * Sets the user status as available
	 * 
	 * @param available
	 * @param status
	 */
	public void setStatus(boolean available, String status) {
		// Defines the type as available
		Presence.Type type = available ? Type.available : Type.unavailable;
		Presence presence = new Presence(type);
		// Set the status
		presence.setStatus(status);
		connection.sendPacket(presence);

	}

	/**
	 * Disconnect the session
	 */
	public void destroy() {
		if (connection != null && connection.isConnected()) {
			connection.disconnect();
			System.out.println("Bye Bye");
		}
	}

	/**
	 * Sends the message and creates a new chat
	 * 
	 * @param message
	 * @param buddyJID
	 * @throws XMPPException
	 */
	public void sendMessage(String message, String buddyJID) throws XMPPException {
		System.out.println(String.format("Enviando mensaje '%1$s' to user %2$s", message, buddyJID));
		// Send the message to the buddy and sets the message listener for the chat
		Chat chat = chatManager.createChat(buddyJID, messageListener);
		chat.sendMessage(message);
	}

	/**
	 * Creates an entry with a buddy for a chat
	 * 
	 * @param user
	 * @param name
	 * @throws Exception
	 */
	public void createEntry(String user, String name) throws Exception {
		System.out.println(String.format("Creando entrada para compañero '%1$s' with name %2$s", user, name));
		// Generates the roster and creates the entry with the buddy
		Roster roster = connection.getRoster();
		roster.createEntry(user, name, null);
	}

	/**
	 * Class with a message and chat listener to create a better performance
	 * 
	 * @author User
	 *
	 */
	class MyMessageListener implements MessageListener, ChatStateListener {

		// When status of the chat change it show the new state
		public void stateChanged(Chat chat, ChatState chatState) {
			if (ChatState.composing.equals(chatState)) {
				System.out.println("Chat State" + chat.getParticipant() + " is typing..");
			} else if (ChatState.gone.equals(chatState)) {
				System.out.println("Chat State" + chat.getParticipant() + " has left the conversation.");
			} else {
				System.out.println("Chat State" + chat.getParticipant() + ": " + chatState.name());
			}
		}

		// Get the messages send in the chat
		public void processMessage(Chat chat, Message message) {
			String from = message.getFrom();
			String body = message.getBody();
			String.format("Recibiendo mensaje '%1$s' from %2$s", body, from);

		}

	}

	/**
	 * Verifies the connection is still connected
	 * 
	 * @return
	 */
	public boolean isConnected() {
		return connection.isConnected();
	}

	/**
	 * Create a new account user, also verifies when an account is already created
	 * 
	 * @param user
	 * @param password
	 */
	public void createAccountForUser(String user, String password) {
		// Set the connection configuration
		ConnectionConfiguration config = new ConnectionConfiguration("alumchat.xyz", 5222);

		// Create the connection
		XMPPConnection connection = new XMPPConnection(config);
		try {
			connection.connect();
		} catch (XMPPException e) {

			e.printStackTrace();
		}

		// Start an accountManager to manage the account creation
		AccountManager accountManager = connection.getAccountManager();
		try {
			// Invoke method to create the account
			accountManager.createAccount(user, password);
		} catch (XMPPException e) {

			System.out.println("error: " + e.getMessage());
		}

		System.out.println("cuenta creada");

	}

	/**
	 * Delete the current session account
	 */
	public void deleteAccountForUser() {
		// Set the connection configuration
		ConnectionConfiguration config = new ConnectionConfiguration("alumchat.xyz", 5222);

		// Create the connection
		XMPPConnection connection = new XMPPConnection(config);
		try {
			connection.connect();
		} catch (XMPPException e) {

			e.printStackTrace();
		}

		// Start an accountManager to manage the current session account delete
		AccountManager accountManager = connection.getAccountManager();
		try {
			accountManager.deleteAccount();
		} catch (XMPPException e) {

			System.out.println("error: " + e.getMessage());
		}

		System.out.println("cuenta eliminada");

	}

	/**
	 * Reads all users and its state
	 */
	public void userConnected() {
		// Generate the roster with the current connection
		Roster roster = connection.getRoster();
		// Gets all the users in the roster
		Collection<RosterEntry> entries = roster.getEntries();
		Presence presence = null;

		// Iterate the entries (users)
		for (RosterEntry entry : entries) {
			// Looks for an specific entry
			presence = roster.getPresence(entry.getUser() + "@alumchat.xyz");

			// Validates if the entry is available or not
			if (presence.getType() == Presence.Type.available) {
				System.out.println("DISPONIBLE " + entry.getUser() + " - " + presence.getType());
			} else {
				System.out.println("DESACTIVADO " + entry.getUser() + " - " + presence.getType());
			}

		}

	}

	/**
	 * Shows the current user information
	 * 
	 * @param user
	 * @throws XMPPException
	 */
	public void userInfo(String user) throws XMPPException {
		// Generate the roster with the current connection
		Roster roster = connection.getRoster();
		// Gets all the users in the roster
		Collection<RosterEntry> entries = roster.getEntries();
		Presence presence = null;
		int flag = 0;

		// Iterate the entries (users)
		for (RosterEntry entry : entries) {

			// Validates the entry is the same to the user of the session
			if (entry.getUser().equalsIgnoreCase(user)) {
				// Looks for the specific entry
				presence = roster.getPresence(user + "@alumchat.xyz");
				VCard card = new VCard();
				card.load(connection, user + "@alumchat.xyz");

				// Validates if it's available or not to show contact's information
				if (presence.getType() == Presence.Type.available) {
					System.out.println("INFO: DISPONIBLE Usuario: " + entry.getUser() + " - Nombre: "
							+ card.getFirstName() + " " + card.getLastName() + " - presencia: " + presence.getType()
							+ " - Alias: " + entry.getName());
				} else {
					System.out.println("INFO: DESACTIVADO Usuario: " + entry.getUser() + " - Nombre: "
							+ card.getFirstName() + " " + card.getLastName() + " - presencia: " + presence.getType()
							+ " - Alias: " + entry.getName());
				}

				// Creates entry with the user
				RosterEntry entry2 = roster.getEntry(user);
				// Search for groups
				for (RosterGroup groups : entry2.getGroups()) {
					System.out.println("Grupo " + groups.getName());
				}

				flag = 1;
			}
		}

		// If user does not exists
		if (flag == 0) {
			System.out.println("USUARIO NO EXISTE");
		}
	}

	/**
	 * Adds contact to a roster group
	 * 
	 * @param user
	 */
	public void addContact(String user) {

		try {
			// Set the subscription mode
			Roster.setDefaultSubscriptionMode(Roster.SubscriptionMode.manual);
			// Creates the connection
			Roster roster = connection.getRoster();

			// Users group review
			if (!roster.contains(user)) {
				// Add contact
				roster.createEntry(user + "@alumchat.xyz", user, new String[] { "Friends" });
				System.out.println("Usuario agregado.");
			} else
				// User is already in the group
				System.out.println("Usuario ya fue agregado.");
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Send file to specific user
	 * 
	 * @throws XMPPException
	 */
	public void sendFile(String fileURL, String user, String message) throws XMPPException {
		// Create the file transfer manager
		FileTransferManager manager = new FileTransferManager(connection);
		// Create the outgoing file transfer
		OutgoingFileTransfer transfer = manager.createOutgoingFileTransfer(user);
		// Send the file
		File file = new File(fileURL);
		transfer.sendFile(file, message);
		while (!transfer.isDone()) {
			if (transfer.getStatus().equals(Status.error)) {
				System.out.println("ERROR!!! " + transfer.getError());
			} else {
				System.out.println(transfer.getStatus());
				System.out.println(transfer.getProgress());
			}
		}

		System.out.println("Success");
	}

	/**
	 * Receive file from user
	 */
	public void recibirArchivo() {
		// Create the file transfer manager
		FileTransferManager manager = new FileTransferManager(connection);
		// Add the file transfer listener
		manager.addFileTransferListener(new FileTransferListener() {
			// Create the file transfer request
			public void fileTransferRequest(final FileTransferRequest request) {
				new Thread() {
					// waits for the incoming file transfer and receive the file
					@Override
					public void run() {
						IncomingFileTransfer transfer = request.accept();
						String home = System.getProperty("user.home");
						File file = new File(home + "/Downloads/" + transfer.getFileName());
						try {
							transfer.recieveFile(file);
							while (!transfer.isDone()) {
								try {
									Thread.sleep(1000L);
								} catch (Exception e) {
									System.out.println("Error: " + e.getMessage());
								}
								if (transfer.getStatus().equals(Status.error)) {
									System.out.println("Error: " + transfer.getError());
								}
								if (transfer.getException() != null) {
									transfer.getException().printStackTrace();
								}
							}
						} catch (Exception e) {
							System.out.println("Error: " + e.getMessage());
						}
					};
				}.start();
			}
		});
	}

	/**
	 * Creates a roster group
	 * 
	 * @param groupName
	 */
	public void createGroup(String groupName) {

		try {
			// Generate the roster with the current connection
			Roster roster = connection.getRoster();
			roster.createGroup(groupName);
			System.out.println("Grupo creado con éxito.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates a multi user chat group
	 * 
	 * @param groupName
	 */
	public void groupChatCreate(String groupName) {
		try {

			// Creates a multi user chat group
			MultiUserChat muc = new MultiUserChat(connection, groupName + "@conference.alumchat.xyz");
			muc.create(groupName);
			// Group configuration
			Form form = muc.getConfigurationForm();
			Form submitForm = form.createAnswerForm();
			for (Iterator<FormField> fields = form.getFields(); fields.hasNext();) {
				FormField field = (FormField) fields.next();
				if (!FormField.TYPE_HIDDEN.equals(field.getType()) && field.getVariable() != null) {
					submitForm.setDefaultAnswer(field.getVariable());
				}
			}

			// Set owners and configurations of the room
			List<String> owners = new ArrayList<String>();
			owners.add(connection.getUser());
			submitForm.setAnswer("muc#roomconfig_roomowners", owners);
			submitForm.setAnswer("muc#roomconfig_persistentroom", true);
			submitForm.setAnswer("muc#roomconfig_roomdesc", groupName);
			muc.sendConfigurationForm(submitForm);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addUserToGroup(String groupName, String user) {

		try {
			// Generate the roster with the current connection
			Roster roster = connection.getRoster();
			// Search for a group if it does not exists creates it
			RosterGroup group = roster.getGroup(groupName + "@alumchat.xyz");
			// Search entries and add them if they are not in the group
			RosterEntry entry = roster.getEntry(user + "@alumchat.xyz");
			if (entry != null) {
				group.addEntry(entry);
				System.out.println("Contacto agregado exitosamente");
			}
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Send message to the multi user chat
	 * 
	 * @param group
	 * @param message
	 */
	public void sendMessageToGroup(String group, String message) {

		Scanner scan = new Scanner(System.in);

		try {
			// Starts the multi user chat
			MultiUserChat muc = new MultiUserChat(connection, group + "@conference.alumchat.xyz");
			// Verifies the group exist
			if (muc != null) {
				// User joins to the chat
				muc.join(connection.getUser().replace("@alumchat.xyz/Smack", ""));
				// Send message
				muc.sendMessage(message);
				// Sets listener
				muc.addMessageListener(new TaxiMultiListener());
				// Keeps sending messages
				while (message.equalsIgnoreCase("EXIT") == false) {
					System.out.println("Ingrese el mensaje");
					message = scan.nextLine();
					muc.sendMessage(message);
				}
				// Leaves the chat
				muc.leave();

				System.out.println("Chat finalizado");
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

	}

	/**
	 * Listener for the group
	 * 
	 * @author User
	 *
	 */
	public static class TaxiMultiListener implements PacketListener {
		// Keeps listening and notifies when it gets a message
		public void processPacket(Packet packet) {
			Message message = (Message) packet;
			String from = message.getFrom();
			String body = message.getBody();
			if (body != null)
				System.out.println(String.format("Receiving message from: '%1$s' from %2$s", body, from));
		}
	}

}
