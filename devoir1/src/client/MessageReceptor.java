package client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * MessageReceptor récupère les messages depuis le serveur dans un thread à part.
 *
 * @author Jian Chen
 * @author Valentin Mention
 */
@SuppressWarnings("InfiniteLoopStatement")
public class MessageReceptor implements Runnable {
    private DataInputStream data;

    /**
     * Initialise une instance de MessageReceptor. En cas d'exception le thread ne démarre pas.
     *
     * @param client le socket utilisé pour la communication avec le serveur
     */
    public MessageReceptor(Socket client) {
        try {
            data = new DataInputStream(client.getInputStream());
        } catch (IOException e) {
            System.err.println("Impossible de créer le flux d'entrée : " + e.getLocalizedMessage());
            System.exit(-2);
        }
    }

    /**
     * Récupère un message depuis le serveur. En cas d'exception le thread s'arrête.
     *
     * @return le String unicode téléchargé
     */
    private String getMessage() {
        String str = null;
        try {
            str = data.readUTF();
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Interruption détectée");
            }
        } catch (IOException e) {
            if(e.getLocalizedMessage().compareTo("Socket closed") == 0) {
                System.out.println("Fermeture du programme..");
            } else {
                System.out.println("Exception détectée, arrêt du thread MessageReceptor");
            }
            System.exit(-1);
        }
        return str;
    }

    /**
     * Lance le thread, qui lit continuellement les messages envoyés par le serveur
     */
    @Override
    public void run() {
        while (true) {
            String received = getMessage();
            if (received != null) {
                System.out.println(received);
            }
        }
    }
}
