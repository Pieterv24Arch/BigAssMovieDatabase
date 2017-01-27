package fx.Controllers;

import javafx.scene.control.Alert;

/**
 * Created by rainslayerx on 1/26/17.
 */
public class Controller {
    public void ShowAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }
}
