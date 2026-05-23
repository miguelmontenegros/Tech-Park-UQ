package co.edu.uniquindio.techparkuq.controlador;

import co.edu.uniquindio.techparkuq.modelo.*;
import co.edu.uniquindio.techparkuq.modelo.abstractas.Empleado;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginController {

    private final Parque parque = ModelFactoryController.getInstance().getParque();
    private String rolSeleccionado = "";

    @FXML private VBox panelCredencial;
    @FXML private Label lblDocumento;
    @FXML private TextField txtDocumento;

    @FXML
    void onAdminClick(ActionEvent event) {
        abrirPanel("/co/edu/uniquindio/techparkuq/vista/PanelAdministrador.fxml", "Admin", null);
    }

    @FXML
    void onOperadorClick(ActionEvent event) {
        rolSeleccionado = "OPERADOR";
        lblDocumento.setText("Documento del Operador:");
        mostrarCredencial(true);
    }

    @FXML
    void onVisitanteClick(ActionEvent event) {
        rolSeleccionado = "VISITANTE";
        lblDocumento.setText("Documento del Visitante:");
        mostrarCredencial(true);
    }

    @FXML
    void onIngresarClick(ActionEvent event) {
        String doc = txtDocumento.getText().trim();
        if (doc.isEmpty()) {
            mostrarAlerta("Campo vacío", "Por favor ingrese un número de documento.");
            return;
        }
        if ("OPERADOR".equals(rolSeleccionado)) {
            Operador op = buscarOperador(doc);
            if (op == null) {
                mostrarAlerta("Operador no encontrado", "No existe operador con documento: " + doc);
                return;
            }
            abrirPanel("/co/edu/uniquindio/techparkuq/vista/PanelOperador.fxml", "Operador", op);
        } else if ("VISITANTE".equals(rolSeleccionado)) {
            Visitante v = buscarVisitante(doc);
            if (v == null) {
                abrirPanel("/co/edu/uniquindio/techparkuq/vista/PanelVisitante.fxml", "NuevoVisitante", doc);
            } else {
                abrirPanel("/co/edu/uniquindio/techparkuq/vista/PanelVisitante.fxml", "Visitante", v);
            }
        }
    }

    private Operador buscarOperador(String documento) {
        for (Empleado e : parque.getListaEmpleados()) {
            if (e instanceof Operador && e.getDocumento().equals(documento)) {
                return (Operador) e;
            }
        }
        return null;
    }

    private Visitante buscarVisitante(String documento) {
        for (Visitante v : parque.getListaVisitantes()) {
            if (v.getDocumento().equals(documento)) {
                return v;
            }
        }
        return null;
    }

    private void abrirPanel(String fxmlPath, String tipo, Object dato) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            if ("Operador".equals(tipo)) {
                PanelOperadorController ctrl = loader.getController();
                ctrl.setOperador((Operador) dato);
            } else if ("Visitante".equals(tipo)) {
                PanelVisitanteController ctrl = loader.getController();
                ctrl.setVisitante((Visitante) dato);
            } else if ("NuevoVisitante".equals(tipo)) {
                PanelVisitanteController ctrl = loader.getController();
                ctrl.setDocumentoNuevo((String) dato);
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Tech-Park UQ — Sistema de Gestión");
            stage.show();

            Stage loginStage = (Stage) panelCredencial.getScene().getWindow();
            if (loginStage != null) loginStage.close();

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error de navegación", "No se pudo abrir el panel:\n" + e.getMessage());
        }
    }

    private void mostrarCredencial(boolean visible) {
        panelCredencial.setVisible(visible);
        panelCredencial.setManaged(visible);
        txtDocumento.clear();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    void onCancelarClick(ActionEvent event) {
        rolSeleccionado = "";
        mostrarCredencial(false);
    }
}