/**
 * 
 */
package ClientPackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import ClientPackage.MessageReceptor;

/**
 * @author Q
 *
 */
public class client {

	public static void main(String[] args) throws IOException {
		Socket sock = new Socket("localhost", 6666);
		
		MessageReceptor rec = new MessageReceptor(sock);
		MessageInput input = new MessageInput(sock);
		
		new Thread(rec).start();
		new Thread(input).start();

	}

}





