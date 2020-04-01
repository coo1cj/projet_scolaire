package alluserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import static java.lang.Math.floor;

public class Server {
	
    public static int jeu_ordi(int nb_allum, int prise_max) {
        int prise = 0;
        int s = 0;
        float t = 0;
        s = prise_max + 1;
        t = ((float)(nb_allum - s)) / (prise_max + 1);
        while (t != floor(t)) {
            s--;
            t = ((float)(nb_allum - s)) / (prise_max + 1);
        }
        prise = s - 1;
        if (prise == 0)
            prise = 1;
        return (prise);
    }
    
    

	public static void main(String[] args) throws IOException, InterruptedException {
		
		ServerSocket listener = new ServerSocket(20500, 50);
		System.out.println("Serveur lancé");
		
		Socket comm = listener.accept();
		System.out.println("Client connecté " + comm.getInetAddress().toString());

		InputStream in = comm.getInputStream();
		DataInputStream datin = new DataInputStream(in);
		
		OutputStream out = comm.getOutputStream();
		DataOutputStream datout = new DataOutputStream(out);
		
		Scanner sc=new Scanner(System.in);
		
		int nb_max_d=0; /*nbre d'allumettes maxi au départ*/
		int nb_allu_max=0; /*nbre d'allumettes maxi que l'on peut tirer au maxi*/
		int qui=0; /*qui joue? 0=Nous --- 1=PC*/
		int prise=0; /*nbre d'allumettes prises par le joueur*/
		int nb_allu_rest=0; /*nbre d'allumettes restantes*/

        /*** RÉCUPÉRATION DES PARAMETRES ***/

        while (true) {
            nb_max_d = datin.readInt();
            if ((nb_max_d < 10) || (nb_max_d > 60)) {
                datout.writeUTF("WRONG VALUE");
            } else {
                datout.writeUTF("OK");
                System.out.println("Nombre total d'allumettes : " + nb_max_d);
                break;
            }
        }

        while (true) {
            nb_allu_max = datin.readInt();
            if ((nb_allu_max >= nb_max_d) || (nb_allu_max <= 0)) {
                datout.writeUTF("WRONG VALUE");
            } else {
                datout.writeUTF("OK");
                System.out.println("Nombre retiré max : " + nb_allu_max);
                break;
            }
        }

        while (true) {
            qui = datin.readInt();
            if ((qui != 0) && (qui != 1)) {
                datout.writeUTF("WRONG VALUE");
            } else {
                if(qui == 0) {
                    System.out.println("Le joueur commence !");
                } else {
                    System.out.println("L'ordinateur commence !");
                }
                datout.writeUTF("OK");
                break;
            }
        }
        nb_allu_rest = nb_max_d;

        datout.writeUTF("BEGIN");

        
        /**** BOUCLE DE JEU ****/
        
        do {
            if (qui == 0) {
                do {
                    
                	while(datin.available() == 0) {  
                		Thread.sleep(20);
                	}
                	
                    prise = datin.readInt();
                	
                    if ((prise > nb_allu_rest) || (prise > nb_allu_max) || prise <= 0) {
                    	datout.writeUTF("MAUVAIS NOMBRE");
                    } else {
                        datout.writeUTF("OK");
                    }
                }
                while ((prise > nb_allu_rest) || (prise > nb_allu_max) || prise <= 0);
                
                /* On répète la demande de prise tant que le nombre donné n'est pas correct */
            } else {
                prise = jeu_ordi(nb_allu_rest, nb_allu_max);
                System.out.println("Prise : " + prise);
                datout.writeInt(prise);
            }
            qui = (qui + 1) % 2;
            nb_allu_rest = nb_allu_rest - prise;
            
        } while (nb_allu_rest > 0);
        
        comm.close();
	}

}
