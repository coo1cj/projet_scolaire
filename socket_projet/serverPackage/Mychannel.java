package serverPackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Mychannel extends Thread{
	Socket client;
	private DataOutputStream dos;
	private DataInputStream dis;
	private boolean flag = true;  //boolean pour la fonction run()
	private int numeroClients;		//le numero de clients pour enregistrer si le premier participant
	private boolean first = true; //pour server diffuser que autre participant rejoint la conversation
	
	public Mychannel(Socket client) throws IOException {
		this.client = client;
		this.numeroClients = server.clients.size();        
		dos = new DataOutputStream(client.getOutputStream());
		dis = new DataInputStream(client.getInputStream());
	}
	
	//methode pour obtenir le nom de thread
	private String getName(Mychannel v) {    
		Set<String> keys = server.clients.keySet();
		String str = "";
		for(String key : keys) {
			if(server.clients.get(key) == v) {
				str = key;
			}
		}	
		return str;
	}
	

	//recevoir des messages d'utilisateur
	private String recevie() throws IOException{
		String str = "";
		try {
			str = dis.readUTF();
		} catch (IOException e) {
			flag = false;                 //s'il y a de probleme, arreter le thread et l'enlever 
			server.clients.remove(this);
			str = "je se deconnecte";     //Si un client se déconnecte anormalement, informez les autres clients qu'il s'est déconnecté
		}
		return str;
	}
	
	//envoyer les messages
	private void send(String str) {
		try {
			dos.writeUTF(str);
			dos.flush();
		} catch (IOException e) {
			flag = false;				//s'il y a de probleme, arreter le thread et l'enlever
			server.clients.remove(this);
			System.out.println(client.getRemoteSocketAddress() + " se deconnecte");
		}	
	}
	
	
	
	private void sendOther() throws IOException {
		Map<String,Mychannel> clients = server.clients;
		Set<String> keys = clients.keySet();
		String name = getName(this);

		if(first && numeroClients != 0) {       //pas de premier participant et diffuser une seule fois
 			for(String key : keys) {
				if(clients.get(key) != this) {
					clients.get(key).send(name + " a rejoint la conversation"); 
				}
			}
			first = false;      //la prochaine fois pas diffuser
		}
	
		String str = this.recevie();
				
		if("exit".equals(str)) {         //si ce utilisateur exit, diffuser le message sur le reste des utilisateurs
			clients.remove(name);
			flag = false;
			for(String key : keys) {
				clients.get(key).send("L'utilisateur " + name + " a quitte la conversation");
			}	
		}else {                         //diffuse ce que ce utilisateur a dit
			for(String key : keys) {
				if(clients.get(key) == this) {
					continue;
				}
				clients.get(key).send(name + " a dit: " + str);
			}
		}
	}
	
	@Override
	public void run() {
		
		while(flag) {
			try {
				sendOther();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(client.getRemoteSocketAddress() + " se deconnecte");
	}
}