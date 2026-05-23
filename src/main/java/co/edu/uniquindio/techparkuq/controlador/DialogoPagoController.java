package co.edu.uniquindio.techparkuq.controlador;

import co.edu.uniquindio.techparkuq.modelo.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.time.LocalDate;

public class DialogoPagoController {

    @FXML private Label lblMontoMostrar;
    @FXML private TextField txtNumTarjeta;
    @FXML private TextField txtNombreTitular;
    @FXML private TextField txtVencimiento;
    @FXML private TextField txtCvv;
    @FXML private ComboBox<String> cmbTipoTarjeta;
    @FXML private VBox panelProcesando;
    @FXML private ProgressBar progressPago;
    @FXML private Label lblEstadoPago;
    @FXML private Label lblMensaje;
    @FXML private Button btnPagar;

    private double monto;
    private boolean pagoAprobado = false;
    private Runnable onAprobado;

    @FXML
    void initialize() {
        cmbTipoTarjeta.setItems(FXCollections.observableArrayList("Visa", "Mastercard", "American Express", "PSE"));
        progressPago.setProgress(ProgressBar.INDETERMINATE_PROGRESS);

        txtNumTarjeta.textProperty().addListener((obs, viejo, nuevo) -> {
            String digits = nuevo.replaceAll("[^\\d]", "");
            if (digits.length() > 16) digits = digits.substring(0, 16);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < digits.length(); i++) {
                if (i > 0 && i % 4 == 0) sb.append("  ");
                sb.append(digits.charAt(i));
            }
            String result = sb.toString();
            if (!result.equals(nuevo)) {
                txtNumTarjeta.setText(result);
                txtNumTarjeta.positionCaret(result.length());
            }
        });

        txtVencimiento.textProperty().addListener((obs, viejo, nuevo) -> {
            String digits = nuevo.replaceAll("[^\\d]", "");
            if (digits.length() > 4) digits = digits.substring(0, 4);
            String result = digits.length() > 2
                    ? digits.substring(0, 2) + "/" + digits.substring(2)
                    : digits;
            if (!result.equals(nuevo)) {
                txtVencimiento.setText(result);
                txtVencimiento.positionCaret(result.length());
            }
        });

        txtCvv.textProperty().addListener((obs, viejo, nuevo) -> {
            String digits = nuevo.replaceAll("[^\\d]", "");
            if (digits.length() > 4) digits = digits.substring(0, 4);
            if (!digits.equals(nuevo)) {
                txtCvv.setText(digits);
                txtCvv.positionCaret(digits.length());
            }
        });
    }

    public void setMonto(double monto) {
        this.monto = monto;
        lblMontoMostrar.setText(String.format("%,.2f", monto));
    }

    public void setOnAprobado(Runnable callback) {
        this.onAprobado = callback;
    }

    public boolean isPagoAprobado() {
        return pagoAprobado;
    }

    @FXML
    void onPagar(ActionEvent event) {
        String numTarjeta = txtNumTarjeta.getText().replaceAll("\\s", "");
        String nombre = txtNombreTitular.getText().trim();
        String venc = txtVencimiento.getText().trim();
        String cvv = txtCvv.getText().trim();
        String tipo = cmbTipoTarjeta.getValue();

        if (numTarjeta.length() < 16) {
            mostrarError("Numero de tarjeta invalido.");
            return;
        }
        if (nombre.isEmpty()) {
            mostrarError("Ingrese el nombre del titular.");
            return;
        }
        if (!venc.matches("\\d{2}/\\d{2}")) {
            mostrarError("Fecha invalida. Formato MM/AA.");
            return;
        }
        if (cvv.length() < 3) {
            mostrarError("CVV invalido.");
            return;
        }
        if (tipo == null) {
            mostrarError("Seleccione el tipo de tarjeta.");
            return;
        }

        try {
            int mes = Integer.parseInt(venc.substring(0, 2));
            int anio = Integer.parseInt(venc.substring(3)) + 2000;
            LocalDate hoy = LocalDate.now();
            if (mes < 1 || mes > 12) { mostrarError("Mes invalido."); return; }
            if (anio < hoy.getYear() || (anio == hoy.getYear() && mes < hoy.getMonthValue())) {
                mostrarError("La tarjeta esta vencida.");
                return;
            }
        } catch (NumberFormatException e) {
            mostrarError("Fecha invalida.");
            return;
        }
        iniciarSimulacion();
    }

    @FXML
    void onCancelar(ActionEvent event) {
        cerrarVentana();
    }

    private void iniciarSimulacion() {
        btnPagar.setDisable(true);
        lblMensaje.setText("");
        panelProcesando.setVisible(true);
        panelProcesando.setManaged(true);

        String[] pasos = {
                "Conectando con el banco...",
                "Verificando datos de la tarjeta...",
                "Autorizando la transaccion...",
                "Confirmando pago..."
        };

        Timeline tl = new Timeline();
        for (int i = 0; i < pasos.length; i++) {
            final String paso = pasos[i];
            tl.getKeyFrames().add(new KeyFrame(Duration.millis(750L * (i + 1)),
                    e -> lblEstadoPago.setText(paso)));
        }

        tl.getKeyFrames().add(new KeyFrame(Duration.millis(750L * (pasos.length + 1)), e -> {
            pagoAprobado = true;
            panelProcesando.setVisible(false);
            panelProcesando.setManaged(false);

            lblMensaje.setStyle("-fx-text-fill: #2E7D32; -fx-font-size: 13; -fx-font-weight: bold;");
            lblMensaje.setText("Pago aprobado! Su saldo fue recargado exitosamente.");

            btnPagar.setDisable(false);
            btnPagar.setText("Cerrar");
            btnPagar.setStyle("-fx-background-color: #2E7D32; -fx-text-fill: white; "
                    + "-fx-font-weight: bold; -fx-padding: 8 20; -fx-background-radius: 5; -fx-font-size: 13;");
            btnPagar.setOnAction(ev -> {
                if (onAprobado != null) onAprobado.run();
                cerrarVentana();
            });
        }));
        tl.play();
    }

    private void mostrarError(String msg) {
        lblMensaje.setStyle("-fx-text-fill: #c62828; -fx-font-size: 12; -fx-font-weight: bold;");
        lblMensaje.setText(msg);
    }

    private void cerrarVentana() {
        ((Stage) btnPagar.getScene().getWindow()).close();
    }
}