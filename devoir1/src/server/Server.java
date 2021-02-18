package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

/**
 * Créé les canaux de communication entre le serveur et les clients
 *
 * @author Jian Chen
 * @author Valentin Mention
 */
public class Server {

    private static int port = 6666;
    protected static final Hashtable<String, Channel> connexionTable = new Hashtable<>();

    /**
     * Méthode principale du serveur, récupère les connexions des clients
     *
     * @param args (optionnel) le port sur lequel le serveur écoute. Par défaut : 6666
     */
    @SuppressWarnings("InfiniteLoopStatement") // It's not a bug, but a feature !
    public static void main(String[] args) {
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException nfe) {
                port = 6666;
            }
        }
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Échec de l'initialisation du socket : " + e.getLocalizedMessage());
            System.exit(-5);
        }
        System.out.println("Server lancé sur le port "+ port);

        while (true) {
            Socket accept;
            try {
                accept = socket.accept();
            } catch (IOException e) {
                System.out.println("Impossible d'accepter la connexion : " + e.getLocalizedMessage());
                continue;
            }

            System.out.println("Nouvelle connexion : " + accept.getRemoteSocketAddress());

            Channel user;
            try {
                user = new Channel(accept, connexionTable);
            } catch (IOException e) {
                System.out.println("Impossible de créer les flux E/S : " + e.getLocalizedMessage());
                continue;
            }
            Thread t = new Thread(user);
            t.start();
        }
    }

}


