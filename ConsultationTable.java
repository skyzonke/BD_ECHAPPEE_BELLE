import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.sql.*;

public class ConsultationTable extends Application {

    public static void main(String[] args) {

        launch(args);
    }

    Connection connection;
    PreparedStatement ps;
    String [] tables={"ROLE","UTILISATEUR","PARTIE","ENIGME","CARTE","SCENARIO","TILESET","TEXTE","TYPETEXTE"};
    String [] idTable={"idro","idut","idpa","iden","idca","idsc","idts","idtxt","idtt"};
    ComboBox<String> cb;
    TextField tf;
    Scene scene;
    GridPane pFormulaire;
    TextField login;
    TextField serveur;
    TextField bd;
    PasswordField mdp;


    GridPane connexion(){
        class ControleurConnexion implements EventHandler<ActionEvent>{
            @Override
            public void handle(ActionEvent actionEvent) {
                try{
                    connection=DriverManager.getConnection("jdbc:mysql://"+serveur.getText()+"/"+bd.getText(),login.getText(),mdp.getText());
                    scene.setRoot(formulaire());
                }catch (SQLException ex){
                    System.out.println("Echec de connection "+ex.getMessage());
                    System.exit(1);
                }
            }

        }
        GridPane vb=new GridPane();
        vb.setPadding(new Insets(5));
        vb.setVgap(5);
        vb.setHgap(5);
        vb.add(new Label("Login:"),1,1);
        vb.add(new Label("Mot de passe:"),1,2);
        vb.add(new Label("Serveur:"),1,3);
        vb.add(new Label("Base de données:"),1,4);
        Button b=new Button("OK");
        b.setOnAction(new ControleurConnexion());
        vb.add(b,1,5);
        login=new TextField("root");
        vb.add(login,2,1);
        mdp=new PasswordField();
        vb.add(mdp,2,2);
        serveur=new TextField();
        serveur.setText("localhost");
        vb.add(serveur,2,3);
        bd=new TextField();
        bd.setText("ECHAPPEE_BELLE");
        vb.add(bd,2,4);
        return vb;
    }

    GridPane formulaire(){
        class ControleurBouton implements EventHandler<ActionEvent>{

            @Override
            public void handle(ActionEvent actionEvent) {
                if (((Button)actionEvent.getSource()).getText()=="Quitter"){
                    System.exit(0);
                }
                String table=cb.getValue();
                if (table==null)
                    return;
                int i = cb.getItems().indexOf(table);
                int cle=-1;
                try{
                    cle=Integer.parseInt(tf.getText());
                } catch (NumberFormatException e) {
                    cle=-1;
                    tf.setText("Entrez un entier");
                }
                VBox vb=afficheEntite(table, idTable[i], cle);
                scene.setRoot(vb);

            }
        }
        GridPane gp = new GridPane();
        gp.setPadding(new Insets(5));
        gp.setHgap(5);
        gp.setVgap(5);
        cb=new ComboBox<String>();
        cb.getItems().addAll(tables);
        cb.setEditable(false);
        tf=new TextField();
        tf.setText("1");
        Button bOk=new Button("OK");
        ControleurBouton cont=new ControleurBouton();
        bOk.setOnAction(cont);
        Button bQuitter=new Button("Quitter");
        bQuitter.setOnAction(cont);
        gp.add(new Label("Table"),1,1);
        gp.add(cb,2,1);
        gp.add(new Label("Identifiant:"),1,2);
        gp.add(tf,2,2);
        gp.add(bOk,1,3);
        gp.add(bQuitter,2,3);
        return gp;
    }

    VBox afficheEntite(String nomTable, String nomCle, int cle){
        class ControleurRetour implements EventHandler<ActionEvent>{

            @Override
            public void handle(ActionEvent actionEvent) {
                scene.setRoot(pFormulaire);
            }
        }
        VBox vb=new VBox(5);

        vb.setPadding(new Insets(5));
        Button b=new Button("Retour");
        b.setOnAction(new ControleurRetour());
        vb.getChildren().add(b);
        try {
            Statement st = connection.createStatement();
            ResultSet rs=st.executeQuery("select * from "+nomTable+ " where "+nomCle+"="+cle);
            if (!rs.next()) {
                vb.getChildren().add(new Label("aucun élément trouvé"));
                return vb;
            }
            ResultSetMetaData md=rs.getMetaData();
            for (int i=1; i<=md.getColumnCount();i++){
                String nom=md.getColumnName(i);
                int type=md.getColumnType(i);
                Label l=new Label(nom+":");
                FlowPane fp=new FlowPane();
                fp.setPadding(new Insets(5));
                fp.setVgap(5);
                fp.setHgap(5);
                fp.getChildren().add(l);
                if (type==Types.BLOB || type==Types.LONGVARBINARY){
                    Image image=new Image(new ByteArrayInputStream(rs.getBytes(i)));
                    ImageView im=new ImageView(image);
                    im.setFitWidth(200);
                    im.setPreserveRatio(true);
                    im.setSmooth(true);
                    fp.getChildren().add(im);
                }else{
                    TextField tf=new TextField();
                    fp.getChildren().add(tf);
                    switch (type) {
                        case Types.BIGINT:
                        case Types.BIT:
                        case Types.BINARY:
                        case Types.INTEGER:
                        case Types.SMALLINT:
                        case Types.TINYINT:
                            tf.setText("" + rs.getInt(i));
                            break;
                        case Types.CHAR:
                        case Types.NCHAR:
                        case Types.NVARCHAR:
                        case Types.VARCHAR:
                            tf.setText(rs.getString(i));
                            tf.setMinSize(tf.getText().length()*10,1);
                            break;
                        case Types.DATE:
                            tf.setText("" + rs.getDate(i));
                            break;
                        case Types.TIME:
                        case Types.TIMESTAMP:
                            tf.setText("" + rs.getTime(i));
                            break;
                        default:
                            tf.setText("Type inconnu " + type);
                    }
                }
                vb.getChildren().add(fp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vb;
    }

    @Override
    public void init(){
        try{
            Class.forName("org.mariadb.jdbc.Driver");
        }catch (ClassNotFoundException ex){
            System.out.println("Driver non trouvé");
            System.exit(1);
        }

        pFormulaire=formulaire();
    }
    @Override
    public void start(Stage stage) {
        scene=new Scene(connexion());
        stage.setTitle("Consultation rudimentaire des tables");
        stage.setWidth(1000);
        stage.setHeight(1000);
        stage.setScene(scene);
        stage.show();
    }
}
