package ClientPackage;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

// pour recevoir des messages de server
public class MessageReceptor extends Thread{
	private DataInputStream dis;
	private boolean flag = true;
	
	public MessageReceptor(Socket client){
		try {
			dis = new DataInputStream(client.getInputStream());
		} catch (IOException e) {
			flag = false;
		}
	}
	
	private String getMessage() {
		String str = "";
		try {
			str = dis.readUTF();
		} catch (IOException e) {
			flag = false;
		}
		return str;
	}
	
	@Override
	public void run() {		
		while(flag) {
			System.out.println(this.getMessage());
		}

	}
}
