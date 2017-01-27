package fx.Controllers;

import fx.VistaNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
/**
 * Main controller class for the entire layout.
 */
public class MainController {
    /** Holder of a switchable vista. */
    @FXML
    private StackPane vistaHolder;

    @FXML
    private HBox topBar;

    /**
     * Replaces the vista displayed in the vista holder with a new vista.
     *
     * @param node the vista node to be swapped in.
     */
    public void setVista(Node node) {
        vistaHolder.getChildren().setAll(node);
    }

    @FXML
    void NavigateToHome(ActionEvent event) {
        VistaNavigator.loadVista(VistaNavigator.HOME);
    }

    @FXML
    void NavigateToParsers(ActionEvent event) {
        VistaNavigator.loadVista(VistaNavigator.PARSERS);
    }

    @FXML
    void NavigateToMapView(ActionEvent event) {
        VistaNavigator.loadVista(VistaNavigator.MAPVIEW);
    }

    @FXML
    void NavigateToResultSet(ActionEvent event) {
        VistaNavigator.loadVista(VistaNavigator.RESULTSET);
    }

    @FXML
    void NavigateToGraphVisualizer(ActionEvent event){VistaNavigator.loadVista(VistaNavigator.GRAPHVIS);}

    @FXML
    private void closeButtonClicked()
    {
        System.exit(0);
    }

    @FXML
    private void minimizeButtonClicked(ActionEvent event)
    {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
}