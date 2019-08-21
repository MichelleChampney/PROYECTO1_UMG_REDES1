package xmpp;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class Menu {
    
    public static int MostrarMenu()
    {
        int lOpcion = 0;
        //Limpiar la consola
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
        System.out.println("<******************** Menu Principal ********************>");
        System.out.println("Administración --------------------");
        System.out.println("1. Registrar una nueva cuenta en el servidor");
        System.out.println("2. Iniciar sesión con una cuenta");
        System.out.println("3. Cerrar sesión con una cuenta");
        System.out.println("4. Eliminar la cuenta del servidor");
        System.out.println("Comunicación --------------------");
        System.out.println("5. Mostrar todos los contactos y su estado");
        System.out.println("6. Agregar un usuario a los contactos");
        System.out.println("7. Mostrar detalles de un contacto");
        System.out.println("8. Comunicación de 1 a 1 con cualquier contacto");
        System.out.println("9. Participar en conversaciones grupales");
        System.out.println("10. Definir mensaje de presencia");
        System.out.println("11. Enviar / recibir notificaciones");
        System.out.println("12. Enviar archivos");
        System.out.println("13. Recibir archivos");
        System.out.println("14. Salir");
        
        Scanner input = new Scanner(System.in);
        System.out.print("\nSeleccion una opción: ");
        lOpcion = input.nextInt();
        
        return lOpcion;
    }
    
    public static void SeleccionarOpcion(int pOpcion) throws XMPPException
    {
        switch(pOpcion)
        {
            //Administracion
            case 1:
                Administracion.RegistrarNuevaCuenta();
                break;
            case 2:
                Administracion.IniciarSesion();
                break;
            case 3:
                Administracion.CerrarSesion();
                break;
            case 4:
                Administracion.EliminarCuenta();
                break;
            //Comunicacion
            case 5:
                Comunicacion.MostrarUsuarios();
                break;
            case 6:
                
                break;
            case 7:
                Comunicacion.MostrarInformacionContacto();
                break;
            case 8:
                break;
            case 9:
                /*System.out.println("Usuario a escribir: ");
                String lUsuarioEnv = scan.nextLine();
                try {
                    Comunicacion.EnviarRecibirNotificacion(XMPP.lConnection,lUsuarioEnv);
                } catch (Exception ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }*/
                break;
            case 10:
                Comunicacion.DefinirMensajePresencia();
                break;
            case 11: 
                Comunicacion.EnviarArchivo(XMPP.gConnection);
                break;
            case 12:
                Archivo.RecibirArchivo(XMPP.gConnection);
                break;
        }
    }
}
