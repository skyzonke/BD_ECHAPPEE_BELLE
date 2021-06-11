import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.*;
import java.util.Locale;

public class HomePage extends Application{
    private Scene scene;
    private TextField login;
    private PasswordField mdp;
    Connection connection;

    public HomePage(){
        try{

            connection=DriverManager.getConnection("jdbc:mysql://"+"localhost"+"/"+"ECHAPPEE_BELLE","root","2T2=bhd!");
        }catch (SQLException ex){
            System.out.println("Echec de connection "+ex.getMessage());
            System.exit(1);
        }
    }

    public BorderPane homePageCreation() {
        class ControleurBt implements EventHandler<ActionEvent> {
            @Override
            public void handle(ActionEvent actionEvent) {
                Button bt = (Button) actionEvent.getSource();
                if (bt.getText().equals("Se connecter")){
                    scene.setRoot(connexionFormulaire());
                }
                else{
                    scene.setRoot(creerCompteFormulaire());
                }
            }

        }

        BorderPane bp = new BorderPane();
        Label lb = new Label("Bienvenue sur l'application d'Echapee Belle");
        BorderPane.setAlignment(lb,Pos.CENTER);
        bp.setTop(lb);
        HBox hb = new HBox();
        Button connex = new Button("Se connecter");
        connex.setOnAction(new ControleurBt());
        hb.getChildren().add(connex);
        Button creerCompte = new Button("Créer un compte");
        creerCompte.setOnAction(new ControleurBt());
        hb.getChildren().add(creerCompte);
        BorderPane.setAlignment(hb, Pos.CENTER);
        bp.setCenter(hb);
        return bp;
    }

    public GridPane connexionFormulaire(){
        class ControleurBtConnex implements EventHandler<ActionEvent> {
            private final HomePage homePage;

            public ControleurBtConnex(HomePage homePage){
                this.homePage = homePage;
            }

            @Override
            public void handle(ActionEvent actionEvent) {
                getLogin().getText();
                getMdp().getText();
                try {
                    PreparedStatement pst = connection.prepareStatement("select * from UTILISATEUR where pseudout=? and mdput=password(?)");
                    pst.setString(1,getLogin().getText());
                    pst.setString(2,getMdp().getText());
                    ResultSet rs=pst.executeQuery();
                    if(rs.next()){
                        PreparedStatement pstRole = connection.prepareStatement("select nomrole from ROLE where idro=?");
                        pstRole.setInt(1,rs.getInt(7));
                        ResultSet rsRole=pst.executeQuery();
                        if(rsRole.next()){
                            switch (rsRole.getString(2).toLowerCase(Locale.ROOT)) {
                                case "admin" -> {
                                    new VueAdministration(this.homePage);
                                }
                                case "joueur" -> {

                                    scene.setRoot(new ModuleJoueur());
                                }
                                case "concepteur" -> {

                                    scene.setRoot(new ModuleConcepteur());
                                }
                            }

                        }

                    }
                    else {
                        scene.setRoot(mdpIncorect());
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


            }

        }
        GridPane gp = new GridPane();
        gp.add(new Label("Nom d'utilisateur:"),1,1);
        gp.add(new Label("Mot de passe:"),1,2);
        this.login=new TextField();
        gp.add(this.login,2,1);
        this.mdp=new PasswordField();
        gp.add(this.mdp,2,2);
        Button b=new Button("Se connecter");
        b.setOnAction(new ControleurBtConnex(this));
        //b.setOnAction(new ControleurConnexion());
        gp.add(b,1,3);
        return gp;
    }

    public GridPane mdpIncorect(){
//        GridPane gp = new GridPane();
//        gp.add(new Label("Nom d'utilisateur:"),1,1);
//        gp.add(new Label("Mot de passe:"),1,2);
//        this.login=new TextField();
//        gp.add(this.login,2,1);
//        this.mdp=new PasswordField();
//        gp.add(this.mdp,2,2);
//        Button b=new Button("Se connecter");
//        b.setOnAction(new ControleurBtConnex(this));
//        //b.setOnAction(new ControleurConnexion());
//        gp.add(b,1,3);
//        return gp;
        return new GridPane();
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Parent scene) {
        this.scene.setRoot(scene);
    }

    public TextField getLogin() {
        return login;
    }

    public PasswordField getMdp() {
        return mdp;
    }

    public GridPane creerCompteFormulaire(){
        GridPane gp = new GridPane();
//        gp.add(new Label("Nom d'utilisateur:"),1,1);
//        gp.add(new Label("Mot de passe:"),1,2);
//        TextField login=new TextField();
//        gp.add(login,2,1);
//        PasswordField mdp=new PasswordField();
//        gp.add(mdp,2,2);
        Button b=new Button("Créer un compte");
        //b.setOnAction(new ControleurConnexion());
        gp.add(b,1,3);
        return gp;
    }



    @Override
    public void start(Stage stage) {
        this.scene=new Scene(homePageCreation());
        stage.setTitle("Echapee Belle");
        stage.setWidth(1000);
        stage.setHeight(1000);
        stage.setScene(scene);
        stage.show();
    }

}
