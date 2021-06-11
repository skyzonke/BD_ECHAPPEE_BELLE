import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.*;

public class InsertionBlob {
    Connection connection;
    PreparedStatement ps;
    InsertionBlob(String login, String mdp, String serveur, String bd){
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            //Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver non trouvé");
            System.exit(1);
        }
        try {
            connection = DriverManager.getConnection("jdbc:mysql://"+serveur+"/"+bd, login, mdp);
        } catch (
                SQLException ex) {
            System.out.println("Echec de connection " + ex.getMessage());
            System.exit(1);
        }
    }
    void ajouterImages(String chemin, String[] tabNom, String requete) {
        try {
            ps = connection.prepareStatement(requete);
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
        for (int i = 0; i < tabNom.length; i++) {
            File f = new File(chemin + tabNom[i]);
            System.out.print(chemin + tabNom[i]);
            try {
                // Lecture du fichier image
                byte[] img = Files.readAllBytes(f.toPath());
                // création du BLOB qui stockera l'image
                Blob blob = connection.createBlob();
                // stockage de l'image dans le BLOB
                blob.setBytes(1, img);
                // affectation du BLOB dans la requête de mise à jour (le 1er ?)
                ps.setBlob(1, blob);
                // affectation de l'identifiant de la ligne qui va stocker le BLOB dans la BD
                ps.setInt(2, i + 1);
                // exécution de la requête de mise à jour
                int nb = ps.executeUpdate();
                System.out.println(" SUCCES");
            } catch (IOException ex) {
                System.out.println("Fichier " + tabNom[i] + " non trouvé");
            } catch (SQLException throwables) {
                System.out.println("Problème SQL " + requete + "\n" + throwables.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        String login, mdp,serveur,bd;
        login=args[0];
        mdp=args[1];
        serveur=args[2];
        bd=args[3];
        String chemin = args[4];
        InsertionBlob ib=new InsertionBlob(login,mdp,serveur,bd);

        String[] tab = {"Tileset.png", "tilesetAtelier.png", "tilesMaison.png"};
        ib.ajouterImages(chemin, tab, "update TILESET set imagets=? where idts=?");
        String[] tab1 = {"1.png", "2.png", "3.png", "4.png", "5.png", "6.png"};
        ib.ajouterImages(chemin, tab1, "update UTILISATEUR set avatarut=? where idut=?");
        String[] tab2 = {"Enigme_addition.png", "Enigme_Carre.png", "Enigme_rebus.png", "Enigme_enfants.png", "Enigme_Telephone.jpg", "Enigme_excalibur.jpg"};
        ib.ajouterImages(chemin, tab2, "update ENIGME set imgen=? where iden=?");
        String[] tab3 = {"icone_jeu1.png", "icone_chateau.png"};
        ib.ajouterImages(chemin, tab3, "update SCENARIO set icone=? where idsc=?");
        String[] tab4 = {"icone_jeu1.png", "victoire.png", "defaite.png", "Enigme_excalibur.jpg", "victoire.png", "defaite.png", "Enigme_excalibur.jpg", "victoire.png", "defaite.png"};
        ib.ajouterImages(chemin, tab4, "update TEXTE set imgtxt=? where idtxt=?");
    }
}
