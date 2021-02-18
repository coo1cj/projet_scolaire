package server;

/**
 * Structure de donnée associant nom d'utilisateur et contenu du message
 *
 * @author Jian Chen
 * @author Valentin Mention
 */
public class Message {
    protected String username;
    protected String data;

    /**
     * Initialise un objet de type Message
     *
     * @param username le nom de l'utilisateur ayant émis le message
     * @param data     le contenu du message
     */
    public Message(String username, String data) {
        this.username = username;
        this.data = data;
    }
}
