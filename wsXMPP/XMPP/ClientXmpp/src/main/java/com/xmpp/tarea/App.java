package com.xmpp.tarea;

import java.util.Scanner;

import com.xmpp.imp.XmppClient;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws Exception {

		/*
		 * Credentials username = "ben1806"; password = "abcd1234"; Extra user for chat
		 * "amanda1"
		 */

		// Initialize variables
		Scanner opcion = new Scanner(System.in);
		String opcionMenu = "";
		String username = "";
		String password = "";
		String buddyJID = "";
		String buddyName = "";
		String userCreate = "";
		String passwordCreate = "";
		String grupo = "";
		String fileURL = "";
		String message = "";

		System.out.println("Iniciar sesión");

		// Get user for login
		System.out.println("Usuario");
		username = opcion.nextLine();

		// Get Password for login
		System.out.println("Contraseña");
		password = opcion.nextLine();

		// Set variables of server and port
		XmppClient xmppManager = new XmppClient("alumchat.xyz", 5222);

		// Initialize connection to the server
		xmppManager.init();
		// Perform login with user and password
		xmppManager.performLogin(username, password);
		// Set status available
		xmppManager.setStatus(true, "Hello everyone");

		// Start with the menu, already logged in
		while (!opcionMenu.equalsIgnoreCase("14")) {

			// Getting option to perform
			System.out.println(
					"1. Iniciar chat /n 2. Crear Usuario /n 3. Eliminar cuenta /n 4. Usuarios Conectados /n 5. Recibir Archivo /n 6. Crear Grupo tipo roster /n 7. Enviar archivo /n 8. Crear grupo MultiChat /n 9. Agregar Contacto /n 10. Cerrar Sesión /n 11. Información de usuario /n 12. Enviar mensaje grupal /n 13. Definir estado de presencia /n 14. Salir");
			opcionMenu = opcion.nextLine();

			// Initialize chat
			if (opcionMenu.equalsIgnoreCase("1")) {
				// Get JID (name of the user)
				System.out.println("Ingrese JID");
				buddyJID = opcion.nextLine();
				// Get buddy name (name of the user)
				System.out.println("Ingrese buddyName");
				buddyName = opcion.nextLine();

				// Create the connection with the buddy
				xmppManager.createEntry(buddyJID, buddyName);

				// Waiting for the user to write a message to send
				Scanner teclado = new Scanner(System.in);

				// Validates the user is still connected
				while (xmppManager.isConnected()) {

					// Receives the message
					String cadena = teclado.nextLine();

					// If message is not empty sends it
					if (!cadena.isEmpty()) {

						// To exit the chat and log out use the word EXIT
						if (cadena.contains("EXIT")) {
							System.out.println("Hasta luego");
							return;

						} else {
							// Sends the message to the buddy
							xmppManager.sendMessage(cadena, buddyJID + "@alumchat.xyz");

						}

					}

				}
				// Close the scanner variable teclado
				teclado.close();

			}
			// Creates new user in the server
			else if (opcionMenu.equalsIgnoreCase("2")) {
				// Receives the user
				System.out.println("Ingrese Usuario");
				userCreate = opcion.nextLine();

				// Receives the password
				System.out.println("Ingrese Contrasena");
				passwordCreate = opcion.nextLine();

				// Invoke Method to create the user
				xmppManager.createAccountForUser(userCreate, passwordCreate);
			} // Delete current account
			else if (opcionMenu.equalsIgnoreCase("3")) {
				// Invoke Method to delete the user account
				xmppManager.deleteAccountForUser();
			} // Shows the basic information of the users
			else if (opcionMenu.equalsIgnoreCase("4")) {
				// Invoke the method and gets the information
				xmppManager.userConnected();
			} // Receives file
			else if (opcionMenu.equalsIgnoreCase("5")) {
				// Invoke the method to receive files
				xmppManager.recibirArchivo();
			} // Create group
			else if (opcionMenu.equalsIgnoreCase("6")) {
				// Waits for the name of the group
				System.out.println("Ingrese nombre del grupo");
				grupo = opcion.nextLine();
				// Invoke the method to create the group
				xmppManager.createGroup(grupo);
				// Waits for the user and add it to the chat
				String agregar = "1";
				while (agregar.equalsIgnoreCase("1")) {
					System.out.println("Ingrese usuario para agregar al grupo");
					userCreate = opcion.nextLine();
					xmppManager.addUserToGroup(grupo, userCreate);
					System.out.println("¿Desea agregar otro usuario al grupo? Presione 1");
					agregar = opcion.nextLine();
				}

			} // send file to specific user
			else if (opcionMenu.equalsIgnoreCase("7")) {
				// Waits for the path of the file
				System.out.println("Ingrese dirección del archivo");
				fileURL = opcion.nextLine();
				// Waits for the name of the user to send the file
				System.out.println("Ingrese usuario a enviar el archivo");
				userCreate = opcion.nextLine();
				// Waits for the message to send
				System.out.println("Ingrese mensaje a enviar");
				message = opcion.nextLine();
				// Invokes the method to send the file
				xmppManager.sendFile(fileURL, userCreate, message);
			} // Create group chat multiuser
			else if (opcionMenu.equalsIgnoreCase("8")) {
				// Waits for the name of the group and create a multiuser chat
				System.out.println("Ingrese nombre para el chat grupal");
				grupo = opcion.nextLine();
				// Invoke the method to create the group
				xmppManager.groupChatCreate(grupo);
			} // Adds a user as contact
			else if (opcionMenu.equalsIgnoreCase("9")) {
				// Waits for the user and add it as a contact
				System.out.println("Ingrese usuario para agregar como contacto");
				userCreate = opcion.nextLine();
				// Invokes the method to add a contact
				xmppManager.addContact(userCreate);
			} // Log out
			else if (opcionMenu.equalsIgnoreCase("10")) {
				// Invokes the method to log out
				xmppManager.destroy();
			} // Contact's information
			else if (opcionMenu.equalsIgnoreCase("11")) {
				// Waits for the user
				System.out.println("Ingrese usuario");
				username = opcion.nextLine();
				// Get user basic information
				xmppManager.userInfo(username);
			} // Send message to group
			else if (opcionMenu.equalsIgnoreCase("12")) {
				// Waits for the group
				System.out.println("Ingrese nombre de grupo");
				grupo = opcion.nextLine();
				// Waits for the message
				System.out.println("Ingrese mensaje");
				message = opcion.nextLine();
				// Invokes the method to send the message
				xmppManager.sendMessageToGroup(grupo, message);
			} // Set current user status
			else if (opcionMenu.equalsIgnoreCase("13")) {
				// Waits for the group
				System.out.println("Ingrese mensaje de presencia");
				message = opcion.nextLine();
				// Set status available
				xmppManager.setStatus(true, message);
			}

		}
		// Close variable scanner opcion
		opcion.close();
	}
}
