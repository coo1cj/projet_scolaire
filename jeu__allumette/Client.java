package alluclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	
	public static void afficher_allu(int n) 
	{
	int i;
	 System.out.print("\n");
	 for (i=0; i<n ;i++)
	  System.out.print("  o");
	 System.out.print("\n");
	  for (i=0; i<n; i++)
	  System.out.print("  |");
	 System.out.print("\n"); 
	  for (i=0; i<n; i++)
	  System.out.print("  |");
	 System.out.print("\n"); 

	}

	private static boolean valeurCorrecte(DataInputStream in) throws java.io.IOException {
		String ret = in.readUTF();
		if (ret.compareTo("OK") == 0) return true;
		else {
			System.out.println("Valeur erronée, réessayez !");
			return false;
		}
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		Socket comm = new Socket("localhost", 20500);
		System.out.println("Connecté !");
		
		OutputStream out = comm.getOutputStream();
		DataOutputStream datout = new DataOutputStream(out);
		
		InputStream in = comm.getInputStream();
		DataInputStream datin = new DataInputStream(in);
		
		Scanner userInput = new Scanner(System.in);
		String userText = "";

		/*** INITIALISATION DU JEU ***/

		int nb_max_d=0; /*nbre d'allumettes maxi au départ*/
		int nb_allu_max=0; /*nbre d'allumettes maxi que l'on peut tirer au maxi*/
		int qui=0; /*qui joue? 0=Nous --- 1=PC*/

		do {
			System.out.println("Nombre d'allumettes disposées entre les deux joueurs (entre 10 et 60) :");
			nb_max_d = userInput.nextInt();
			datout.writeInt(nb_max_d);
		}
		while (!valeurCorrecte(datin));

		do {
			System.out.println("\nNombre maximal d'allumettes que l'on peut retirer : ");
			nb_allu_max = userInput.nextInt();
			datout.writeInt(nb_allu_max);
		}
		while (!valeurCorrecte(datin));

		do {
			System.out.println("\nQuel joueur commence? 0--> Joueur ; 1--> Ordinateur : ");
			qui = userInput.nextInt();
			datout.writeInt(qui);
		}
		while (!valeurCorrecte(datin));

		System.out.println("Le jeu peut commencer !");

		/*** Le jeu commence ***/

		datin.readUTF(); // on lit le "BEGIN"
		int input = -1;

		if(qui == 1) {
			input = datin.readInt();
			nb_max_d -= input;
			System.out.println("L'ordinateur retire " + input + " allumettes, il en reste " + nb_max_d);
		}

		userInput.nextLine();
		
		while(!comm.isClosed()) {

			do {
				System.out.println("Entrez un nombre d'allumettes à retirer :");
				userText = userInput.nextLine();
				input = Integer.parseInt(userText);
				datout.writeInt(input);
			} while (!valeurCorrecte(datin));

			nb_max_d -= input;
			System.out.println("Vous retirez " + input + " allumettes, il en reste " + nb_max_d);

			if (nb_max_d <= 0) {
				System.out.println("Vous avez perdu !");
				break;
			}

			input = datin.readInt();
			nb_max_d -= input;
			System.out.println("L'ordinateur retire " + input + " allumettes, il en reste " + nb_max_d);

			if (nb_max_d <= 0) {
				System.out.println("Vous avez gagné !");
				break;
			}
		}
		
		
	}
	


}

