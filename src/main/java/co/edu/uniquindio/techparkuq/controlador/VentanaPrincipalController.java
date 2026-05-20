package co.edu.uniquindio.techparkuq.controlador;

import co.edu.uniquindio.techparkuq.modelo.Parque;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

        System.out.println("SISTEMA: VentanaPrincipalController vinculada exitosamente a " + parque.getNombre());
    }


    @FXML
    void onAdmitirVisitanteClick(ActionEvent event) {
        System.out.println("UI_LOG: Solicitando apertura de formulario de admisión.");
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