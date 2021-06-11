insertion du jeu d'essai
------------------------
* Connectez vous à votre serveur de base de données comme super utilisateur
  sudo mysql
* exécutez le script de création de la base de données
  source echappee_belle.sql
* puis la création du jeu d'essai
  source jeuessai_echappee_belle.sql
* n'oubliez pas de donner les droits à votre utilisateur de base de données (replacez <login> par le login de votre utilisateur de BD
  grant ALL on ECHAPPEE_BELLE.* to <login>;

insertion des images dans la base de données
--------------------------------------------
* compilez le fichier InsertionBlob
  javac InsertionBlob.java
* executez ce programme qui a 4 arguments:
  - login: votre login de base de données
  - mdp: le mot de passe de base de données
  - serveur: le serveur de la base de données (localhost)
  - bd: le nom de la base de données (ECHAPPEE_BELLE)
  - chemin_vers_images: le chemin vers les images de l'archive (./IMAGES/)
  Attention remplacez /usr/share/java/mariadb-java-client.jar par le chemin vers de driver JDBC que vous utilisez. Si vous utilisez un driver MySQL décommentez la ligne 12 du fichier InsertionBlob.java et commentez la ligne 11.
  java -cp .:/usr/share/java/mariadb-java-client.jar InsertionBlob limet limet localhost ECHAPPEE_BELLE IMAGES/
  
consultation des tables de la base de données:
----------------------------------------------

Le programme ConsultationTable permet de consulter la plupart des tables de la base de données et notamment de voir les images qui y sont stockée.
* Compilez ce programme (ATTENTION remplacez le module-path par le chemin vers votre installation de JavaFX:
  javac --module-path /usr/share/openjfx/lib/ --add-modules javafx.controls ConsultationTable.java
* Exécutez ce programme
  java -cp .:/usr/share/java/mariadb-java-client.jar --module-path /usr/share/openjfx/lib/ --add-modules javafx.controls ConsultationTable
