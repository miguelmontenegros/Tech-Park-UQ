package co.edu.uniquindio.techparkuq.controlador;

import co.edu.uniquindio.techparkuq.modelo.Parque;
import co.edu.uniquindio.techparkuq.modelo.Visitante;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class RegistroVisitanteController {

    private final ModelFactoryController mfc;
    private final Parque parque;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private TextField txtDocumento;

    @FXML
    private TextField txtEdad;

    @FXML
    private TextField txtEstatura;

    @FXML
    private TextField txtNombre;

    public RegistroVisitanteController() {
        this.mfc = ModelFactoryController.getInstance();
        this.parque = mfc.getParque();
    }

    @FXML
    void onGuardarClick(ActionEvent event) {
        try {
            // 1. Capturar los datos de los TextFields
            String nombre = txtNombre.getText().trim();
            String documento = txtDocumento.getText().trim();
            String edadStr = txtEdad.getText().trim();
            String estaturaStr = txtEstatura.getText().trim();


            if (nombre.isEmpty() || documento.isEmpty() || edadStr.isEmpty() || estaturaStr.isEmpty()) {
                mostrarAlerta("Campos Incompletos", "Por favor, diligencie todos los campos.", Alert.AlertType.WARNING);
                return;
            }

            int edad = Integer.parseInt(edadStr);
            double estatura = Double.parseDouble(estaturaStr);

            Visitante nuevoVisitante = new Visitante(nombre, documento, edad, estatura, "");

            boolean exito = parque.admitirVisitante(nuevoVisitante);

            if (exito) {
                mostrarAlerta("Registro Exitoso", "El visitante " + nombre + " fue registrado.", Alert.AlertType.INFORMATION);
                limpiarCampos();
            } else {
                mostrarAlerta("Error", "Capacidad máxima alcanzada.", Alert.AlertType.ERROR);
            }

        } catch (NumberFormatException e) {
            mostrarAlerta("Formato Erróneo", "La edad es un número entero y la estatura decimal.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onLimpiarClick(ActionEvent event) {
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtDocumento.clear();
        txtEdad.clear();
        txtEstatura.clear();
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    @FXML
    void initialize() {
        assert btnGuardar != null : "fx:id=\"btnGuardar\" was not injected: check your FXML file.";
        assert btnLimpiar != null : "fx:id=\"btnLimpiar\" was not injected: check your FXML file.";
        assert txtDocumento != null : "fx:id=\"txtDocumento\" was not injected: check your FXML file.";
        assert txtEdad != null : "fx:id=\"txtEdad\" was not injected: check your FXML file.";
        assert txtEstatura != null : "fx:id=\"txtEstatura\" was not injected: check your FXML file.";
        assert txtNombre != null : "fx:id=\"txtNombre\" was not injected: check your FXML file.";
    }
}
