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
        System.out.println("4. Eliminar cuenta del servidor");
        System.out.println("Comunicación --------------------");
        System.out.println("5. Mostrar todos los usuarios y su estado");
        System.out.println("6. Agregar un usuario a los contactos");
        System.out.println("7. Mostrar detalles de contacto de un usuario");
        System.out.println("8. Participar en conversaciones grupales");
        System.out.println("9. Enviar / recibir notificaciones");
        System.out.println("10. Enviar archivos");
        System.out.println("11. Recibir archivos");
        System.out.println("12. Salir");
        
        Scanner input = new Scanner(System.in);
        System.out.print("\nSeleccion una opción: ");
        lOpcion = input.nextInt();
        
        return lOpcion;
    }
    
    public static void SeleccionarOpcion(int pOpcion, ConnectionConfiguration pConfiguracion) throws XMPPException
    {
        String[] lUsuario = new String[1];
        String[] lClave = new String[1];
        Scanner scan = new Scanner(System.in);
        
        switch(pOpcion)
        {
            //Administracion
            case 1:
                SolicitarDatosUsuario(lUsuario,lClave);
                Administracion.RegistrarNuevaCuenta(pConfiguracion, lUsuario[0], lClave[0]);
                System.out.println("Cuenta registrada correctamente.");
                break;
            case 2:
                SolicitarDatosUsuario(lUsuario,lClave);
                XMPP.lConnection = Administracion.IniciarSesion(pConfiguracion, lUsuario[0], lClave[0]);
                System.out.println("Sesión iniciada correctamente.");
                break;
            case 3:
                Administracion.CerrarSesion(XMPP.lConnection);
                break;
            case 4:
                Administracion.EliminarCuenta(pConfiguracion, XMPP.lConnection);
                System.out.println("Cuenta eliminada correctamente.");
                break;
            //Comunicacion
            case 5:
                Comunicacion.MostrarUsuarios(XMPP.lConnection);
                break;
            case 6:
                break;
            case 7:
                Comunicacion.MostrarInformacionContacto(XMPP.lConnection);
                break;
            case 8:
                break;
            case 9:
                System.out.println("Usuario a escribir: ");
                String lUsuarioEnv = scan.nextLine();
                try {
                    Comunicacion.EnviarRecibirNotificacion(XMPP.lConnection,lUsuarioEnv);
                } catch (Exception ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 10:
                Comunicacion.EnviarArchivo(XMPP.lConnection);
                break;
            case 11:
                Archivo.RecibirArchivo(XMPP.lConnection);
                break;
        }
    }
    
    private static void SolicitarDatosUsuario(String[] pUsuario, String[] pClave)
    {
        Scanner scan = new Scanner(System.in);
        System.out.print("Ingrese el nombre del usuario: ");
        pUsuario[0] = scan.nextLine();
        System.out.print("Ingrese el nombre la clave: ");
        pClave[0] = scan.nextLine();
    }
}
