package xmpp;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;

public class Administracion extends Thread {
    
    public static void RegistrarNuevaCuenta(ConnectionConfiguration pConfiguracion, String pUsuario, String pClave)
    {
        try
        {
            XMPPConnection lConnection = new XMPPConnection(pConfiguracion);
            lConnection.connect(); 
            AccountManager lManager = lConnection.getAccountManager();
            lManager.createAccount(pUsuario,pClave);
            lConnection.disconnect();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public static XMPPConnection IniciarSesion(ConnectionConfiguration pConfiguracion, String pUsuario, String pClave)
    {
        XMPPConnection lConnection = null;
        try
        {
            lConnection = new XMPPConnection(pConfiguracion);
            lConnection.connect(); 
            lConnection.login(pUsuario, pClave);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return lConnection;
    }
    
    public static void CerrarSesion(XMPPConnection pConnection)
    {
        try
        {
            pConnection.disconnect();
            pConnection = null;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public static void EliminarCuenta(ConnectionConfiguration pConfiguracion, XMPPConnection pConnection)
    {
        try
        {
            AccountManager lManager = pConnection.getAccountManager();
            lManager.deleteAccount();
            pConnection = null;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
