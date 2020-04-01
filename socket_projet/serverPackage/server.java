package serverPackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ClientPackage.client;

public class server {
	public static Map<String,Mychannel> clients = new HashMap<String,Mychannel>();
	private static final int SERVER_PORT = 6666;
	
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket ss = new ServerSocket(SERVER_PORT); 
        System.out.println("server is running...");
        for (;;) {
            Socket sock = ss.accept();
            
            System.out.println("connected from " + sock.getRemoteSocketAddress());
                      
            addChannel add = new addChannel(sock);
            add.start();
            
        }
    
    }

    
}


