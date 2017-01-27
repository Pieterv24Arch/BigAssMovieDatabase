package fx.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by rainslayerx on 1/26/17.
 */
public class MapViewController extends Controller implements Initializable {
    @FXML
    private WebView mapView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mapView.setContextMenuEnabled(false);
        WebEngine webEngine = mapView.getEngine();
        webEngine.load("https://pieterdev.net/birthmap");
    }
}
