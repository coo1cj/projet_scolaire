package client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Ouvre la communication avec le serveur et créé les threads MessageInput et MessageReceptor
 *
 * @author Jian Chen
 * @author Valentin Mention
 */
public class Client {
    private static int port = 6666;
    private static String addr;

    /**
     * Méthode principale du client. Le port ne peut-être spécifié sans l'adresse IP
     *
     * @param args <p>(optionnel) l'adresse IP sur laquelle joindre le serveur. Par défaut : 127.0.0.1 </p>
     *             <p>(optionnel) le port sur lequel contacter le serveur. Par défaut : 6666 </p>
     */
    public static void main(String[] args) {
        if (args.length == 1) {
            try {
                addr = InetAddress.getByName(args[0]).getHostAddress();
            } catch (UnknownHostException uhe) {
                addr = "localhost";
            }
        } else if (args.length > 1) {
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException nfe) {
                port = 6666;
            }
        }

        Socket sock = null;
        try {
            sock = new Socket(addr, port);
        } catch (IOException e) {
            System.out.println("Impossible de créer le socket : " + e.getLocalizedMessage());
            System.exit(-5);
        }

        System.out.println("Connecté sur serveur à l'IP " + sock.getInetAddress().toString() + " sur le port " + port);

        MessageReceptor rec = new MessageReceptor(sock);
        MessageInput input = new MessageInput(sock);

        new Thread(rec).start();
        new Thread(input).start();
    }
}





