# Devoir1 de SR03

Une application de messagerie simple constitué d'un serveur et de plusieurs clients.

## Prérequis

* Java SE version 8 au minimum est nécessaire à l'exécution des fichiers jar.
* Java JDK version 8 au minimum est nécessaire pour compiler les sources

## Utilisation

Les fichiers binaires peuvent être récupérés depuis la page de release : https://gitlab.utc.fr/sr03_chen_mention/devoir1/-/releases  
Ils doivent être exécutés depuis un terminal avec la commande :

~~~
java -jar [Client-vx.x/Server-vx.x.jar]
~~~

Lancer le serveur en premier, le port peut être spécifié en premier argument de l'appel.  
Les clients doivent utiliser le même port que le serveur.  

**Liste des commandes client**:
* /exit : déconnecte et quitte le programme en cours 
* /list : affiche la liste des utilisateurs connectés
* /[username] [msg] : envoie un message privé à un utilisateur en ligne
* /help : affiche cette liste

## Documentation

Documentation disponible en ligne : https://sr03_chen_mention.gitlab.utc.fr/devoir1/  
Pour un accès hors connexion, cloner le projet et ouvrir /src/javadoc/index.html

## Compilation

Pour compiler les fichiers sources et générer les fichiers .class et .jar, exécuter le fichier build.sh à la racine du projet.

## Rapport

La source du rapport est disponible au format Latex dans le fichier "rapport.tex".

## Auteurs

* **Jian Chen**
* **Valentin Mention**

