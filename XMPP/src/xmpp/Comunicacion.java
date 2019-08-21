package xmpp;

import java.util.Collection;
import java.util.Scanner;
import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Type;
import org.jivesoftware.smackx.packet.VCard;

public class Comunicacion {
    
    public static void MostrarUsuarios()
    {
        Roster roster = XMPP.gConnection.getRoster();
        Collection<RosterEntry> entries = roster.getEntries();
        System.out.println("Usuario\t\tEstado");
        for (RosterEntry entry : entries) {
            System.out.println(entry.getName() + "\t\t" + entry.getStatus());
        }
    }
     
    public static void MostrarInformacionContacto()
    {
        try
        {
            Scanner scan = new Scanner(System.in);
            System.out.println("Ingrese el contacto: ");
            String lContacto = scan.nextLine();
            
            VCard card = new VCard();
            card.load(XMPP.gConnection, lContacto);
            System.out.println("Last Name: " + card.getLastName());
            
            /*AccountManager lManager = XMPP.gConnection.getAccountManager();

            System.out.println("Nombre: " + lManager.getAccountAttribute("name"));
            System.out.println("Correo: " + lManager.getAccountAttribute("email "));
            System.out.println("Fecha de registro: " + lManager.getAccountAttribute("date"));
            System.out.println("Estado: " + lManager.getAccountAttribute("state"));
            System.out.println("Descripción: " + lManager.getAccountAttribute("text"));*/
        
         }catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
        }
    }
    
    public static void DefinirMensajePresencia()
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("Ingrese su mensaje de presencia: ");
        String lMensajePresencia = scan.nextLine();
        
        Presence lPresence = new Presence(Presence.Type.available);
        lPresence.setStatus(lMensajePresencia);
        XMPP.gConnection.sendPacket(lPresence);
    }
    
    public static void EnviarRecibirNotificacion(XMPPConnection pConnection, String pUsuarioId) throws XMPPException, Exception
    {
        createEntry(pConnection, pUsuarioId, pUsuarioId.replace("alumchat.xyz",""),null);
        ChatManager chatManager = pConnection.getChatManager();
        Chat chat = chatManager.createChat(pUsuarioId, new MyMessageListener());
        chat.sendMessage("holo");
        System.out.println(String.format("Sending mesage '%1$s' to user %2$s", "holo", pUsuarioId));
    }
    
    public static void createEntry(XMPPConnection pConnection, String pUser, String pName, String pGrupo) throws Exception {
        System.out.println(String.format("Creating entry for buddy '%1$s' with name %2$s", pUser, pName));
        Roster roster = pConnection.getRoster();
        roster.createEntry(pUser, pName, (pGrupo == null) ? new String[] { pGrupo } : null);
    }
    
    public static void EnviarArchivo(XMPPConnection pConnection)
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("Ingrese el id del usuario a enviar el archivo: ");
        String lUsuario = scan.nextLine();
        System.out.println("Ingrese la ubicación y nombre del archivo: ");
        String lArchivo = "C:\\Users\\miche\\OneDrive\\Escritorio\\redes.txt";
        System.out.println("Ingrese la descripción del archivo: ");
        String lDescripcion = scan.nextLine();
        
        Archivo.EnviarArchivo(pConnection,lUsuario, lArchivo, lDescripcion);
    }
    
    static class MyMessageListener implements MessageListener {
    @Override
    public void processMessage(Chat chat, Message message) {
        String from = message.getFrom();
        String body = message.getBody();
        System.out.println(String.format("Received message '%1$s' from %2$s", body, from));
    }
         
    }
}