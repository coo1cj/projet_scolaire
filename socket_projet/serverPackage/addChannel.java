package serverPackage;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class addChannel extends Thread{
	private DataInputStream dis;
	private DataOutputStream dos;
	private Socket client;
	private boolean isSaisir = true;   //verifier si l'utilisateur se deconnecte avant saisir le nom
	
	public addChannel(Socket client) throws IOException {
		dos = new DataOutputStream(client.getOutputStream());
		dis = new DataInputStream(client.getInputStream());
		this.client = client;
	}
	
		
	
	@Override
	public void run() {
		String str = null;
		
		try {
			dos.writeUTF("Entrer votre pseudo:");
		    dos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			Thread.sleep(10); //attendre la saisie
			str = dis.readUTF();
			
			dos.writeUTF(str + " a rejoint la conversation");
		    dos.writeUTF("---------------------");
		    dos.flush();
		    
			Mychannel channel = new Mychannel(this.client); //creer un nouveau thread entre client et server
			server.clients.put(str, channel);  //ajouter le nouveau thread a map
			channel.start(); 
			
		} catch (IOException e) {
			isSaisir = false;
			System.out.println(client.getRemoteSocketAddress() + " se deconnect");  //si le client se deconncte avant de saisir un nom, server affiche l'erreur
		} catch (InterruptedException e) {
			isSaisir = false;
			System.out.println(client.getRemoteSocketAddress() + " se deconnect");
		}
		
			

	}
}
