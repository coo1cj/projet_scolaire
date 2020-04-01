#include<stdio.h>
#include<stdlib.h>
#include<time.h>
#include<unistd.h>

#define ligne 20
#define colonne 30

void initialise_damier(int M[ligne][colonne]){

    srand(time(NULL));
    for(int i=0;i<ligne;i++)
	 for(int j=0;j<colonne;j++)
	   M[i][j]=rand()%2;
}

void afficher(int M[ligne][colonne])
{

 for(int i=0;i<ligne;i++)
  {
      for(int j=0;j<colonne;j++)
	 printf("%d ",M[i][j]);
      printf("\n");
  }

 printf("------------------- \n");
}
int nb_voisin(int M[ligne][colonne],int i,int j)
{
 int nb_voisin=0;
 for(int k=i-1;k<i+2;k++)
  {
     if(M[(k+ligne)%ligne][(j-1+colonne)%colonne]!=0)nb_voisin++;
     if(M[(k+ligne)%ligne][(j+1)%colonne]!=0)nb_voisin++;
     if(((k+ligne)%ligne)!=i) if(M[(k+ligne)%ligne][j]!=0)nb_voisin++;
  }
return nb_voisin;
}

void jeu_de_vie(int M[ligne][colonne]){
 int voisin=0;
 int vecteur[ligne][colonne];
 for(int i=0;i<ligne;i++)
  for(int j=0;j<colonne;j++)
   {
     voisin=nb_voisin(M,i,j); 
     switch (voisin)
      {
	case 2:vecteur[i][j]=M[i][j];break;
	case 3:vecteur[i][j]=1;break;
	default: vecteur[i][j]=0;break;
      }
   }

 for(int i=0;i<ligne;i++)
  for(int j=0;j<colonne;j++)
   M[i][j]=vecteur[i][j];

}

int main(){
 int mat[ligne][colonne];
 initialise_damier(mat);
 printf("generation initiale: \n");
 afficher(mat);
 int nb_iteration;
 printf("entrer le nombre d'itération:");
 scanf("%d",&nb_iteration);

 for(int i=0;i<nb_iteration;i++)
  {
  	jeu_de_vie(mat);
  	afficher(mat);
  	usleep(500000);
  	system("clear");
  }
}
