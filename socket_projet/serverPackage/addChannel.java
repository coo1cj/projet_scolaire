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
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
			

	}
}
