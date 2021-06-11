import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class VueAdministration extends BorderPane {
    private HomePage app;

    public VueAdministration(HomePage app){
        super();
        this.app = app;
        this.setTop(new Label("Adminstrateur"));
        final ControllerAdministration ctAdmin = new ControllerAdministration(this);

        Button Deco = new Button("Déconnexion ");
        Button GereUtilisateur = new Button("GereUtilisaur");
        Button GereScenario = new Button("GereSenario");
        Deco.setOnAction(ctAdmin);GereScenario.setOnAction(ctAdmin);GereUtilisateur.setOnAction(ctAdmin);
        VBox h = new VBox();
        h.getChildren().add(GereUtilisateur);
        h.getChildren().add(GereScenario);
        h.getChildren().add(Deco);

        TextField text = new TextField();
        Button trier = new Button("trier");
        VBox val = new VBox();
        val.getChildren().add(text);
        val.getChildren().add(trier);

        BorderPane page = new BorderPane() ;
        page.setLeft(h);
        page.setCenter(val);
        this.setLeft(page);
        this.app.setScene(this);
    }

    public HomePage getApp() {
        return app;
    }
}
