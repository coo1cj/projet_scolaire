package ClientPackage;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

//pour envoyer des messages de client
public class MessageInput extends Thread{
		private DataOutputStream dos;
		private BufferedReader br;
		private boolean flag = true;
		Socket client;
		public MessageInput(Socket client) throws IOException {
			this.client = client;
			br = new BufferedReader(new InputStreamReader(System.in));
			dos = new DataOutputStream(client.getOutputStream());
		}
		
		private String readstr() {
			
			String str = "";
			try {
				str = br.readLine();
			} catch (IOException e) {
				flag = false;
			}
			return str;
		}
		
		private void send(String str) {
			try {
				dos.writeUTF(str);
				dos.flush();
				if("exit".equals(str)) {  // si envoyer le message "exit", deconnecter la connexion de server
					this.client.close();
					flag = false;
				}
			} catch (IOException e) {
				flag = false;
			}
		}
		
		@Override
		public void run() {
			while(flag) {
				this.send(readstr());
			}
			
			
		}

}

