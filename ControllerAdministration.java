import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.awt.*;

public class ControllerAdministration implements EventHandler<ActionEvent> {
    private VueAdministration vue;

    public ControllerAdministration(VueAdministration vue) {
        this.vue = vue;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        System.out.println(actionEvent.getSource().getClass());
        if (actionEvent.getSource() instanceof javafx.scene.control.Button){
            Button bt = (Button) actionEvent.getSource();
            System.out.println(bt.getText());
            switch (bt.getText()){
                case "Déconnexion" -> vue.getApp().setScene(vue.getApp().homePageCreation());
                case "GereUtilisaur" -> vue.getApp().setScene(vue);
                case "GereSenario" -> vue.getApp().setScene(vue);

            }
        }
    }
}
