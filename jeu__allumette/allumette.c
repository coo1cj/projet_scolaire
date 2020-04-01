#include<stdio.h>
#include<math.h>


int jeu_ordi (int nb_allum, int prise_max)
{
int prise = 0;
int s = 0;
float t = 0;
s = prise_max + 1;
t = ((float) (nb_allum - s)) / (prise_max + 1);
while (t != floor (t))
  {
   s--;
   t = ((float) (nb_allum-s)) / (prise_max + 1);
  }
prise = s - 1;
if (prise == 0)
prise = 1;

printf("l'ordi a pris %d \n",prise);
return (prise);
}



int jeu_joueur (int nb_allum, int prise_max)
{

int prise;

printf("nombre d'allumette restant: %d \n entrer le nombre d\'allumette que vous voulez prendre:",nb_allum);
scanf ("%d",&prise);

while ((prise>prise_max)||(prise>nb_allum))
scanf("%d",&prise);
printf("vous avez pris %d \n",prise);
return (prise);
}

void afficher_allumette( int nb_allum){

for(int i=0;i<nb_allum;i++)
printf("° ");
printf("\n");
for(int i=0;i<nb_allum;i++)
printf("| ");
printf("\n");

}


int main(){

int nb_allumette,maximum_prise,premier;

printf("entrer le nombre d'allumette: (entre 10 et 60)");
scanf("%d",&nb_allumette);
printf("entrer le nombre de prise maximal par tour: ");
scanf("%d",&maximum_prise);

printf("qui commence: tapper 0 pour le joueur et 1 pour l'ordi");
scanf("%d",&premier);


while(nb_allumette>0)
{ 
switch (premier){
case 0:  nb_allumette-=jeu_joueur(nb_allumette,maximum_prise);
	if(nb_allumette==0){printf("vous perdez \n");continue;} 
	nb_allumette-=jeu_ordi(nb_allumette,maximum_prise);
	afficher_allumette(nb_allumette);	
	if(nb_allumette==0)printf("ordi perd \n"); 
	break;
case 1: nb_allumette-=jeu_ordi(nb_allumette,maximum_prise); 
	if(nb_allumette==0){printf("ordi perd \n");continue;} 
	nb_allumette-=jeu_joueur(nb_allumette,maximum_prise);
	afficher_allumette(nb_allumette);	
	if(nb_allumette==0)printf("vous perdez \n"); 
	
	break;
}
}


return 0;
}
