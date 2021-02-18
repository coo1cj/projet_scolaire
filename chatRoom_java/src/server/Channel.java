package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Gère la communication entre le serveur et un client
 *
 * @author Jian Chen
 * @author Valentin Mention
 */
public class Channel implements Runnable {

    private DataOutputStream outputStream;
    private DataInputStream inputStream;
    private final Hashtable<String, Channel> users;
    private String username;

    private Socket socket;
    private boolean running;

    /**
     * Créé un canal associé à un client par un socket
     *
     * @param socket le socket reliant l'utilisateur au serveur
     * @param users  la table des utilisateurs
     * @throws IOException lance une exception non gérée si échec de création des flux E/S
     */
    public Channel(Socket socket, Hashtable<String, Channel> users) throws IOException {
        this.socket = socket;
        this.users = users;
        this.outputStream = new DataOutputStream(socket.getOutputStream());
        this.inputStream = new DataInputStream(socket.getInputStream());
        this.username = null;
        this.running = false;
    }

    /**
     * Déconnecte un utilisateur et le supprime de la table users
     */
    private void disconnect() {
        if (username != null) {
            System.out.println(socket.toString() + " déconnecté");
            users.remove(username);
            serverBroadcast(username + " déconnecté");
            username = null;
        } else {
            if (running) System.out.println(socket.toString() + " déconnecté");
        }
        try {
            socket.close();
        } catch (IOException ignored) {}
        running = false;
    }

    /**
     * Initialise la communication avec le client et récupère son nom d'utilisateur
     */
    private void init() {
        try {
            do {
                outputStream.writeUTF("Entrez votre nom d'utilisateur :");
                String buff = inputStream.readUTF();
                if (!setUsername(buff)) outputStream.writeUTF("Mauvais login ou login déjà utilisé !");
            } while (username == null);
            serverBroadcast(username + " est connecté");
            users.put(username, this);
        } catch (IOException e) {
            disconnect();
        }

        send("Vous êtes connecté en tant que " + username);
        send("--------------------");
    }

    /**
     * Tente d'assigner un nom d'utilisateur normalisé au client
     *
     * @param username le nom d'utilisateur
     * @return false si le nom existe déjà où est invalide, true si l'opération réussi
     */
    private boolean setUsername(String username) {
        if (!checkString(username)) return false;
        username = username.replaceAll("\\s+", "");
        username = Normalizer.normalize(username, Normalizer.Form.NFD);
        synchronized (users) {
            if (users.containsKey(username)) {
                return false;
            }
            this.username = username;
            return true;
        }
    }

    /**
     * Récupère un message depuis le socket
     *
     * @return le String unicode téléchargé
     */
    private String receive() {
        String data = null;
        try {
            data = inputStream.readUTF();
        } catch (IOException e) {
            disconnect();
        }
        return data;
    }

    /**
     * Envois un String unicode au client associé à ce canal
     *
     * @param message le message à envoyer
     */
    private void send(String message) {
        if (!checkString(message)) return;
        try {
            outputStream.writeUTF(message);
            outputStream.flush();
        } catch (IOException e) {
            disconnect();
        }
    }

    /**
     * Envois un String unicode à un client spécifié
     *
     * @param message le message à envoyer
     * @param target  le destinataire du message
     */
    private boolean privateSend(String message, String target) {
        if (!users.containsKey(target) || !checkString(message)) {
            return false;
        } else {
            users.get(target).send(">> " + username + " : " + message);
            return true;
        }
    }

    /**
     * Diffuse un message à tous les utilisateurs sauf l'auteur du message
     *
     * @param message l'objet à transmettre
     */
    private void broadcast(Message message) {
        synchronized (users) {
            users.forEach((username, channel) -> {
                if (username.compareTo(this.username) != 0) channel.send(message.username + ": " + message.data);
            });
        }
    }

    /**
     * Diffuse un message anonyme à tous les utilisateurs
     *
     * @param message le message à transmettre
     */
    private void serverBroadcast(String message) {
        synchronized (users) {
            users.forEach((username, channel) -> channel.send(message));
        }
    }

    /**
     * Vérifie qu'un String n'est pas null ou vide de tout caractère lisible
     *
     * @param s le String à vérifier
     * @return true si le String est valide
     */
    private boolean checkString(String s) {
        if (s == null) return false;
        String buff = s.replaceAll("\\s+", "");
        return buff.length() != 0;
    }

    /**
     * Récupère la liste des utilisateurs actuellement connectés
     *
     * @return une ArrayList de Strings contenant le nom de tous les utilisateurs connectés
     */
    private ArrayList<String> getUserList() {
        synchronized (users) {
            ArrayList<String> userList = new ArrayList<>();
            users.forEach((username, channel) -> userList.add(username));
            return userList;
        }
    }

    /**
     * Analyse la commande entrée par l'utilisateur pour action
     *
     * @param cmd un String commençant par '/'
     */
    private void execCommand(String cmd) {
        switch (cmd) {
            case "/exit": // commande gérée côté client, ne devrait pas arriver ici
                disconnect();
                break;
            case "/list":
                ArrayList<String> userList = getUserList();
                StringBuilder list = new StringBuilder();
                list.append("Liste des utilisateurs connectés : \n");
                for (String user : userList) {
                    list.append(user).append("\n");
                }
                send(list.toString());
                break;
            case "/help":
                send("Liste des commandes :\n" +
                        "/exit : quitte le programme en cours\n" +
                        "/list : affiche la liste des utilisateurs connectés\n" +
                        "/[username] [msg]: envoie un message privé à un utilisateur\n" +
                        "/help : affiche cette aide");
                break;
            default:
                cmd = cmd.substring(1);
                String[] arr = cmd.split(" ", 2);
                if (!users.containsKey(arr[0])) {
                    send("Commande ou utilisateur inconnu ! /help pour la liste des commandes");
                } else {
                    if (!privateSend(arr[1], arr[0])) {
                        send("Message pour " + arr[0] + " vide ou incorrect !");
                    }
                }
                break;
        }
    }

    /**
     * Lance et organise l'interraction client/serveur
     */
    @Override
    public void run() {
        init();
        running = true;
        while (running) {
            Message received = new Message(username, receive());
            if (checkString(received.data)) {
                if (received.data.charAt(0) == '/') {
                    execCommand(received.data);
                } else {
                    broadcast(received);
                }
            }
        }
    }
}
