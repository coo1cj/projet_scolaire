package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Gère la saisie du texte par l'utilisateur dans un thread à part
 *
 * @author Jian Chen
 * @author Valentin Mention
 */
@SuppressWarnings("InfiniteLoopStatement")
public class MessageInput implements Runnable {
    private DataOutputStream outputStream;
    private BufferedReader reader;
    private Socket client;

    /**
     * Initialise une instance de MessageInput
     *
     * @param client le socket utilisé pour la communication avec le serveur
     */
    public MessageInput(Socket client) {
        this.client = client;
        reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            outputStream = new DataOutputStream(client.getOutputStream());
        } catch (IOException e) {
            System.err.println("Impossible de créer le flux de sortie : " + e.getLocalizedMessage());
            System.exit(-3);
        }
    }

    /**
     * Récupère les messages de l'utilisateur
     *
     * @return un String contenant l'entrée utilisateur
     */
    private String readstr() {
        String str = null;
        try {
            str = reader.readLine();
        } catch (IOException e) {
            System.out.println("Exception détectée, arrêt du thread MessageInput.");
            System.exit(-2);
        }
        return str;
    }

    /**
     * Envoie un String unicode au serveur
     *
     * @param message le String à envoyer
     */
    private void send(String message) {
        if (message == null) return;
        try {
            if ("/exit".equals(message)) { // Déconnexion du serveur
                client.close();
                System.exit(0);
            }
            outputStream.writeUTF(message);
            outputStream.flush();
        } catch (IOException e) {
            System.out.println("Exception détectée, arrêt du thread MessageInput.");
            System.exit(-2);
        }
    }

    /**
     * Lance le thread qui envoie les messages du client au serveur
     */
    @Override
    public void run() {
        while (true) {
            String str = readstr();
            if (str != null && str.compareTo("") != 0 && str.compareTo("\n") != 0) {
                this.send(str);
            }
        }
    }

}

