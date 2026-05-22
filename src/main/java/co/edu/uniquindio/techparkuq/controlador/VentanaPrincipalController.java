package co.edu.uniquindio.techparkuq.controlador;

import co.edu.uniquindio.techparkuq.modelo.Parque;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VentanaPrincipalController {

    private final ModelFactoryController mfc;
    private final Parque parque;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane panelPrincipal;

    @FXML
    private Button btnAdmitirVisitante;

    @FXML
    private Button btnCambiarClima;

    @FXML
    private Button btnGenerarReporte;

    @FXML
    private Button btnRegistrarUso;

    public VentanaPrincipalController() {
        this.mfc = ModelFactoryController.getInstance();
        this.parque = mfc.getParque();
    }

    @FXML
    void initialize() {
        assert btnAdmitirVisitante != null : "fx:id=\"btnAdmitirVisitante\" was not injected: check your FXML file.";
        assert btnCambiarClima != null : "fx:id=\"btnCambiarClima\" was not injected: check your FXML file.";
        assert btnGenerarReporte != null : "fx:id=\"btnGenerarReporte\" was not injected: check your FXML file.";
        assert btnRegistrarUso != null : "fx:id=\"btnRegistrarUso\" was not injected: check your FXML file.";
        assert panelPrincipal != null : "fx:id=\"panelPrincipal\" was not injected: check your FXML file.";

        System.out.println("SISTEMA: VentanaPrincipalController vinculada exitosamente a " + parque.getNombre());
    }

    @FXML
    void onAdmitirVisitanteClick(ActionEvent event) {
        cambiarContenidoCentral("/co/edu/uniquindio/techparkuq/vistas/VentanaVisitante.fxml");
    }

    private void cambiarContenidoCentral(String rutaFxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFxml));
            Parent view = loader.load();

            panelPrincipal.getChildren().clear();
            panelPrincipal.getChildren().add(view);

            AnchorPane.setTopAnchor(view, 0.0);
            AnchorPane.setBottomAnchor(view, 0.0);
            AnchorPane.setLeftAnchor(view, 0.0);
            AnchorPane.setRightAnchor(view, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onRegistrarUsoClick(ActionEvent event) {
        System.out.println("UI_LOG: Solicitando registro de ingreso a atracción.");
    }

    @FXML
    void onCambiarClimaClick(ActionEvent event) {
        System.out.println("UI_LOG: Abriendo controles de alerta climática.");
    }

    @FXML
    void onGenerarReporteClick(ActionEvent event) {
        System.out.println("UI_LOG: Ejecutando recopilación estadística del parque.");
    }
}