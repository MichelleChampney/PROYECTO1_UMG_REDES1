package xmpp;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class XMPP {

    /**
     * @param args the command line arguments
     */
    
    public static XMPPConnection lConnection = null;
            
    public static void main(String[] args) throws XMPPException {
        
        int lOpcion = 0;
        ConnectionConfiguration lConfiguration = Conexion.ConfigurarConexion();
        
        while (lOpcion <= 11)
        {
            lOpcion = Menu.MostrarMenu();
            Menu.SeleccionarOpcion(lOpcion, lConfiguration);
        }
        
        System.out.println("<******************** Gracias por utilizar la aplicacion ********************>");
    }
}
