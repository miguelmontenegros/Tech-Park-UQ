package co.edu.uniquindio.techparkuq.controlador;

import co.edu.uniquindio.techparkuq.modelo.*;
import co.edu.uniquindio.techparkuq.modelo.abstractas.Atraccion;
import co.edu.uniquindio.techparkuq.modelo.enums.EstadoAtraccion;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class PanelOperadorController {

    private final Parque parque = ModelFactoryController.getInstance().getParque();
    private Operador operador;

    @FXML private Label lblInfoOperador;
    @FXML private Label lblZonaInfo;
    @FXML private TableView<Atraccion> tblMisAtracciones;
    @FXML private TableColumn<Atraccion, String> colOpNombre;
    @FXML private TableColumn<Atraccion, String> colOpEstado;
    @FXML private TableColumn<Atraccion, Integer> colOpContador;
    @FXML private TableColumn<Atraccion, Integer> colOpEspera;
    @FXML private TableColumn<Atraccion, Double> colOpAltura;
    @FXML private TableColumn<Atraccion, Integer> colOpEdad;

    @FXML private ComboBox<Atraccion> cmbCtrlAtraccion;
    @FXML private Label lblEstadoActual;
    @FXML private ComboBox<EstadoAtraccion> cmbNuevoEstado;
    @FXML private TextField txtMotivoCierre;
    @FXML private Label lblResultadoControl;

    @FXML private ComboBox<Atraccion> cmbMantAtraccion;
    @FXML private Label lblMantEstado;
    @FXML private Label lblMantContador;
    @FXML private Label lblNecesitaRevision;
    @FXML private TextArea areaLogMant;

    @FXML private ComboBox<Visitante> cmbVisitante;
    @FXML private ComboBox<Atraccion> cmbValidarAtraccion;
    @FXML private Label lblResultadoAcceso;

    @FXML private ComboBox<Atraccion> cmbColaAtraccion;
    @FXML private ComboBox<Visitante> cmbColaVisitante;
    @FXML private Label lblColaTiempoEspera;
    @FXML private Label lblColaTamanio;
    @FXML private ListView<String> listCola;
    @FXML private Label lblColaResultado;

    @FXML
    void initialize() {
        configurarTablaAtracciones();
        cmbNuevoEstado.setItems(FXCollections.observableArrayList(EstadoAtraccion.values()));
    }

    private void refrescarUI() {
        tblMisAtracciones.refresh();
        cargarAtracciones();
        configurarCombos();
        refrescarVistaCola();
    }

    public void setOperador(Operador operador) {
        this.operador = operador;
        actualizarVista();
    }

    private void actualizarVista() {
        if (operador == null) return;
        String zona = operador.getZonaAsignada() != null ? operador.getZonaAsignada().getNombre() : "Sin asignar";
        lblInfoOperador.setText("Operador: " + operador.getNombre() + "  |  " + zona);
        lblZonaInfo.setText("Zona: " + zona);
        cargarAtracciones();
        configurarCombos();
    }

    private void configurarTablaAtracciones() {
        colOpNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        colOpEstado.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEstado().toString()));
        colOpContador.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getContadorUso()).asObject());
        colOpEspera.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getTiempoEsperaEstimado()).asObject());
        colOpAltura.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getAlturaMinima()).asObject());
        colOpEdad.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getEdadMinima()).asObject());
    }

    private void configurarCombos() {
        StringConverter<Atraccion> convAtr = new StringConverter<>() {
            @Override public String toString(Atraccion a) { return a != null ? a.getNombre() : ""; }
            @Override public Atraccion fromString(String s) { return null; }
        };
        cmbCtrlAtraccion.setConverter(convAtr);
        cmbMantAtraccion.setConverter(convAtr);
        cmbValidarAtraccion.setConverter(convAtr);
        cmbColaAtraccion.setConverter(convAtr);

        cmbVisitante.setConverter(new StringConverter<>() {
            @Override public String toString(Visitante v) { return v != null ? v.getNombre() + " (" + v.getDocumento() + ")" : ""; }
            @Override public Visitante fromString(String s) { return null; }
        });
        cmbColaVisitante.setConverter(new StringConverter<>() {
            @Override public String toString(Visitante v) { return v != null ? v.getNombre() + " (" + (v.getTicket() != null ? v.getTicket().getClass().getSimpleName() : "Sin ticket") + ")" : ""; }
            @Override public Visitante fromString(String s) { return null; }
        });

        if (operador == null) return;
        var atrs = FXCollections.observableArrayList(operador.getListAtraccionesGestionadas());

        Atraccion ctrlSel = cmbCtrlAtraccion.getValue();
        Atraccion mantSel = cmbMantAtraccion.getValue();
        Atraccion valSel = cmbValidarAtraccion.getValue();
        Atraccion colaSel = cmbColaAtraccion.getValue();
        Visitante visSel = cmbVisitante.getValue();
        Visitante colaVisSel = cmbColaVisitante.getValue();

        cmbCtrlAtraccion.setItems(atrs);
        cmbMantAtraccion.setItems(atrs);
        cmbValidarAtraccion.setItems(atrs);
        cmbColaAtraccion.setItems(atrs);

        var visitantes = FXCollections.observableArrayList(parque.getListaVisitantes());
        cmbVisitante.setItems(visitantes);
        cmbColaVisitante.setItems(visitantes);

        if (atrs.contains(ctrlSel)) cmbCtrlAtraccion.setValue(ctrlSel);
        if (atrs.contains(mantSel)) cmbMantAtraccion.setValue(mantSel);
        if (atrs.contains(valSel)) cmbValidarAtraccion.setValue(valSel);
        if (atrs.contains(colaSel)) cmbColaAtraccion.setValue(colaSel);
        if (visitantes.contains(visSel)) cmbVisitante.setValue(visSel);
        if (visitantes.contains(colaVisSel)) cmbColaVisitante.setValue(colaVisSel);
    }

    private void cargarAtracciones() {
        if (operador == null) return;
        tblMisAtracciones.setItems(FXCollections.observableArrayList(operador.getListAtraccionesGestionadas()));
    }

    @FXML
    void onRefrescarAtracciones(ActionEvent event) {
        refrescarUI();
    }

    @FXML
    void onSeleccionarAtraccion(ActionEvent event) {
        Atraccion a = cmbCtrlAtraccion.getValue();
        if (a != null) lblEstadoActual.setText(a.getEstado().toString());
    }

    @FXML
    void onCambiarEstado(ActionEvent event) {
        Atraccion a = cmbCtrlAtraccion.getValue();
        EstadoAtraccion nuevo = cmbNuevoEstado.getValue();
        if (a == null || nuevo == null) {
            alerta("Selección incompleta", "Seleccione atracción y nuevo estado.");
            return;
        }
        operador.cambiarEstadoAtraccion(a, nuevo);
        if (nuevo == EstadoAtraccion.CERRADA) {
            String motivo = txtMotivoCierre.getText().trim();
            a.setMotivoCierre(motivo.isEmpty() ? "Cerrada por operador." : motivo);
        }
        ModelFactoryController.getInstance().guardarDatosSerializable();
        refrescarUI();
        lblEstadoActual.setText(nuevo.toString());
        lblResultadoControl.setText("Estado actualizado a: " + nuevo);
    }

    @FXML
    void onSeleccionarAtraccionMant(ActionEvent event) {
        Atraccion a = cmbMantAtraccion.getValue();
        if (a == null) return;
        lblMantEstado.setText(a.getEstado().toString());
        lblMantContador.setText(String.valueOf(a.getContadorUso()));
        if (a.requiereMantenimiento()) {
            lblNecesitaRevision.setText("SÍ ⚠");
            lblNecesitaRevision.setStyle("-fx-text-fill: #c62828; -fx-font-weight: bold;");
        } else {
            lblNecesitaRevision.setText("No");
            lblNecesitaRevision.setStyle("-fx-text-fill: #2E7D32; -fx-font-weight: bold;");
        }
    }

    @FXML
    void onRegistrarRevision(ActionEvent event) {
        Atraccion a = cmbMantAtraccion.getValue();
        if (a == null) { alerta("Sin selección", "Seleccione una atracción."); return; }
        a.registrarRevisionTecnica();
        ModelFactoryController.getInstance().guardarDatosSerializable();
        refrescarUI();
        areaLogMant.appendText("• Revisión completada: " + a.getNombre() + "  |  Fecha: " + java.time.LocalDate.now() + "\n");
        lblMantEstado.setText(a.getEstado().toString());
        lblMantContador.setText(String.valueOf(a.getContadorUso()));
        lblNecesitaRevision.setText("No");
        lblNecesitaRevision.setStyle("-fx-text-fill: #2E7D32; -fx-font-weight: bold;");
        info("Revisión registrada", a.getNombre() + " vuelve a estado ACTIVA.");
    }

    @FXML
    void onValidarAcceso(ActionEvent event) {
        Visitante v = cmbVisitante.getValue();
        Atraccion a = cmbValidarAtraccion.getValue();
        if (v == null || a == null) {
            alerta("Selección incompleta", "Seleccione visitante y atracción.");
            return;
        }
        boolean ok = operador.validarAcceso(v, a);
        if (ok) {
            lblResultadoAcceso.setText("✓ ACCESO PERMITIDO  — " + v.getNombre());
            lblResultadoAcceso.setStyle("-fx-text-fill: #2E7D32; -fx-font-weight: bold; -fx-font-size: 14;");
            parque.registrarUsoAtraccion(v, a);
            ModelFactoryController.getInstance().guardarDatosSerializable();
            refrescarUI();
        } else {
            lblResultadoAcceso.setText("✗ ACCESO DENEGADO  — " + v.getNombre());
            lblResultadoAcceso.setStyle("-fx-text-fill: #c62828; -fx-font-weight: bold; -fx-font-size: 14;");
        }
    }

    @FXML
    void onSeleccionarAtraccionCola(ActionEvent event) {
        refrescarVistaCola();
    }

    @FXML
    void onAgregarACola(ActionEvent event) {
        Atraccion a = cmbColaAtraccion.getValue();
        Visitante v = cmbColaVisitante.getValue();
        if (a == null || v == null) {
            alerta("Selección incompleta", "Seleccione atracción y visitante.");
            return;
        }
        if (v.getTicket() == null) {
            alerta("Sin ticket", v.getNombre() + " no tiene ticket activo.");
            return;
        }
        boolean ok = operador.validarAcceso(v, a);
        if (!ok) {
            lblColaResultado.setText("✗ No se puede agregar: Acceso denegado por restricciones.");
            lblColaResultado.setStyle("-fx-text-fill: #c62828; -fx-font-weight: bold;");
            return;
        }
        ColaVirtual cola = a.getColaVirtual();
        if (cola.getListEspera().contains(v)) {
            alerta("Ya en cola", v.getNombre() + " ya está en la cola de " + a.getNombre() + ".");
            return;
        }
        cola.agregarVisitante(v);
        ModelFactoryController.getInstance().guardarDatosSerializable();
        refrescarUI();
        lblColaResultado.setText("✓ " + v.getNombre() + " agregado a la cola de " + a.getNombre());
        lblColaResultado.setStyle("-fx-text-fill: #2E7D32; -fx-font-weight: bold;");
    }

    @FXML
    void onAtenderSiguiente(ActionEvent event) {
        Atraccion a = cmbColaAtraccion.getValue();
        if (a == null) { alerta("Sin selección", "Seleccione una atracción."); return; }
        ColaVirtual cola = a.getColaVirtual();
        Visitante siguiente = cola.atenderSiguiente();
        if (siguiente != null) {
            lblColaResultado.setText("✓ Atendiendo a: " + siguiente.getNombre() + "  |  Ticket: " + siguiente.getTicket().getClass().getSimpleName());
            lblColaResultado.setStyle("-fx-text-fill: #00542E; -fx-font-weight: bold;");
            parque.registrarUsoAtraccion(siguiente, a);
            ModelFactoryController.getInstance().guardarDatosSerializable();
            refrescarUI();
        } else {
            lblColaResultado.setText("La cola está vacía.");
            lblColaResultado.setStyle("-fx-text-fill: #666;");
            refrescarVistaCola();
        }
    }

    @FXML
    void onRefrescarCola(ActionEvent event) {
        refrescarVistaCola();
    }

    private void refrescarVistaCola() {
        Atraccion a = cmbColaAtraccion.getValue();
        if (a == null) return;
        ColaVirtual cola = a.getColaVirtual();
        lblColaTamanio.setText("Personas en espera: " + cola.getListEspera().size());
        lblColaTiempoEspera.setText("Tiempo estimado: " + cola.getTiempoEstimado() + " min");

        listCola.getItems().clear();
        int pos = 1;
        for (Visitante v : cola.getListEspera()) {
            String tipo = v.getTicket() != null ? v.getTicket().getClass().getSimpleName() : "Sin ticket";
            String prefijo = (v.getTicket() instanceof TicketFastPass) ? "⭐ " : "   ";
            listCola.getItems().add(prefijo + pos + ". " + v.getNombre() + "  [" + tipo + "]");
            pos++;
        }
    }

    @FXML
    void onVolverLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/techparkuq/vista/Login.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Tech-Park UQ — Inicio");
            stage.show();
            ((Stage) lblInfoOperador.getScene().getWindow()).close();
        } catch (Exception e) {
            alerta("Error", "No se pudo volver al login: " + e.getMessage());
        }
    }

    private void alerta(String titulo, String msg) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle(titulo); a.setHeaderText(null); a.setContentText(msg); a.showAndWait();
    }

    private void info(String titulo, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(titulo); a.setHeaderText(null); a.setContentText(msg); a.showAndWait();
    }
}