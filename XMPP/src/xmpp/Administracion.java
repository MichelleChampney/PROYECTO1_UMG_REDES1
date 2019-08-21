package xmpp;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;

public class Administracion extends Thread {
    
    public static void RegistrarNuevaCuenta()
    {
        try
        {
            Scanner scan = new Scanner(System.in);
            System.out.print("Ingrese el nombre del usuario: ");
            String lNombreUsuario = scan.nextLine();
            System.out.print("Ingrese el usuario: ");
            String lUsuario = scan.nextLine();
            System.out.print("Ingrese la clave: ");
            String lClave = scan.nextLine();
            System.out.print("Ingrese una descripcion breve de la cuenta: ");
            String lDescripcion = scan.nextLine();
         
            Map<String, String> map = new HashMap<String, String>();
            map.put("name", lNombreUsuario);
            map.put("text", lDescripcion);
            
            XMPPConnection lConnection = new XMPPConnection(XMPP.gConfiguration);
            lConnection.connect(); 
            AccountManager lManager = lConnection.getAccountManager();
            lManager.createAccount(lUsuario,lClave,map);
            lConnection.disconnect();
            
            System.out.println("Cuenta registrada correctamente.");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public static void IniciarSesion()
    {
        try
        {
            Scanner scan = new Scanner(System.in);
            System.out.print("Ingrese el nombre del usuario: ");
            String lUsuario = scan.nextLine();
            System.out.print("Ingrese la clave: ");
            String lClave = scan.nextLine();
            
            XMPP.gConnection = new XMPPConnection(XMPP.gConfiguration);
            XMPP.gConnection.connect(); 
            XMPP.gConnection.login(lUsuario, lClave);
            
            System.out.println("Sesión iniciada correctamente.");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public static void CerrarSesion()
    {
        try
        {
            XMPP.gConnection.disconnect();
            XMPP.gConnection = null;
            
            System.out.println("Sesión cerrada correctamente.");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public static void EliminarCuenta()
    {
        try
        {
            AccountManager lManager = XMPP.gConnection.getAccountManager();
            lManager.deleteAccount();
            XMPP.gConnection.disconnect();
            XMPP.gConnection = null;
            
            System.out.println("Cuenta eliminada correctamente.");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
