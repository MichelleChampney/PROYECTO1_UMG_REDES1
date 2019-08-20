package xmpp;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;

public class Conexion {
 
    public static ConnectionConfiguration ConfigurarConexion()
    {
        ConnectionConfiguration lConfiguration = new ConnectionConfiguration("alumchat.xyz",5222);
        lConfiguration.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        lConfiguration.setDebuggerEnabled(false);
        lConfiguration.setSendPresence(true);
        return lConfiguration;
    }
    
    public static boolean EstaConectado()
    {
        return (XMPP.lConnection != null && XMPP.lConnection.isConnected());   
    }
}
