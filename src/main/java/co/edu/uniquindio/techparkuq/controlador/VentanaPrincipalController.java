package co.edu.uniquindio.techparkuq.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class VentanaPrincipalController {

    private ModelFactoryController mfc = ModelFactoryController.getInstance();

    @FXML
    private Button btnAdmitirVisitante;

    @FXML
    void onAdmitirVisitanteClick(ActionEvent event) {
        System.out.println("LOG: Se presionó admitir visitante en el parque " + mfc.getParque().getNombre());
    }

    @FXML
    void initialize() {
        assert btnAdmitirVisitante != null : "fx:id=\"btnAdmitirVisitante\" was not injected: check your FXML file.";
    }
}