package co.edu.uniquindio.techparkuq.controlador;

import co.edu.uniquindio.techparkuq.modelo.*;
import co.edu.uniquindio.techparkuq.modelo.abstractas.Atraccion;
import co.edu.uniquindio.techparkuq.modelo.abstractas.Ticket;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class PanelVisitanteController {

    private final Parque parque = ModelFactoryController.getInstance().getParque();
    private Visitante visitante;

    @FXML private Label     lblNombreVisitante;
    @FXML private TextField txtNombre;
    @FXML private TextField txtDocumento;
    @FXML private TextField txtEdad;
    @FXML private TextField txtEstatura;
    @FXML private Label     lblSaldo;
    @FXML private TextField txtMontoCarga;
    @FXML private Label     lblTicketInfo;

    @FXML private ComboBox<String> cmbTipoTicket;
    @FXML private HBox             panelFamiliar;
    @FXML private TextField        txtNumIntegrantes;
    @FXML private TextField        txtPrecioBase;
    @FXML private Label            lblCostoFinal;
    @FXML private Label            lblResultadoTicket;

    @FXML private TableView<Atraccion>           tblAtracciones;
    @FXML private TableColumn<Atraccion, String>  colVisNombre;
    @FXML private TableColumn<Atraccion, String>  colVisTipo;
    @FXML private TableColumn<Atraccion, String>  colVisEstado;
    @FXML private TableColumn<Atraccion, Integer> colVisEspera;
    @FXML private TableColumn<Atraccion, Double>  colVisCosto;
    @FXML private TableColumn<Atraccion, Double>  colVisAlt;

    @FXML private TableView<Atraccion>           tblFavoritos;
    @FXML private TableColumn<Atraccion, String>  colFavNombre;
    @FXML private TableColumn<Atraccion, String>  colFavTipo;
    @FXML private TableColumn<Atraccion, String>  colFavEstado;


    @FXML
    void initialize() {
        configurarTablas();
        cmbTipoTicket.setItems(FXCollections.observableArrayList("General", "Familiar", "Fast-Pass"));
        cargarAtracciones();
    }

    public void setVisitante(Visitante v) {
        this.visitante = v;
        cargarPerfilVisitante();
        cargarFavoritos();
        tblAtracciones.refresh();
    }

    public void setDocumentoNuevo(String documento) {
        txtDocumento.setText(documento);
        lblNombreVisitante.setText("Nuevo Visitante — Complete su perfil");
    }


    private void configurarTablas() {
        colVisNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colVisTipo.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getClass().getSimpleName()));
        colVisEstado.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getEstado().toString()));
        colVisEspera.setCellValueFactory(c -> {
            if (visitante != null) {
                int t = c.getValue().getTiempoEsperaParaVisitante(visitante);
                return new javafx.beans.property.SimpleIntegerProperty(t).asObject();
            }
            return new javafx.beans.property.SimpleIntegerProperty(
                    c.getValue().getTiempoEsperaEstimado()).asObject();
        });
        colVisCosto.setCellValueFactory(new PropertyValueFactory<>("costoAdicional"));
        colVisAlt.setCellValueFactory(new PropertyValueFactory<>("alturaMinima"));

        colFavNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colFavTipo.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getClass().getSimpleName()));
        colFavEstado.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getEstado().toString()));
    }

    private void cargarPerfilVisitante() {
        if (visitante == null) return;
        txtNombre.setText(visitante.getNombre());
        txtDocumento.setText(visitante.getDocumento());
        txtEdad.setText(String.valueOf(visitante.getEdad()));
        txtEstatura.setText(String.valueOf(visitante.getEstatura()));
        lblSaldo.setText(String.format("%.2f", visitante.getSaldoVirtual()));
        lblNombreVisitante.setText("Visitante: " + visitante.getNombre());
        actualizarInfoTicket();
    }

    private void actualizarInfoTicket() {
        if (visitante == null) return;
        Ticket t = visitante.getTicket();
        if (t != null) {
            lblTicketInfo.setText(t.getClass().getSimpleName()
                    + " | ID: " + t.getIdTicket()
                    + " | Costo total: $" + String.format("%.2f", t.calcularCostoFinal()));
        } else {
            lblTicketInfo.setText("Sin ticket activo.");
        }
    }

    private void cargarAtracciones() {
        tblAtracciones.setItems(FXCollections.observableArrayList(parque.getListaAtracciones()));
    }

    private void cargarFavoritos() {
        if (visitante == null) return;
        tblFavoritos.setItems(FXCollections.observableArrayList(visitante.getListaFavoritos()));
    }


    @FXML
    void onGuardarPerfil(ActionEvent event) {
        String nombre    = txtNombre.getText().trim();
        String doc       = txtDocumento.getText().trim();
        String edadStr   = txtEdad.getText().trim();
        String estatStr  = txtEstatura.getText().trim();

        if (nombre.isEmpty() || doc.isEmpty() || edadStr.isEmpty() || estatStr.isEmpty()) {
            alerta("Campos incompletos", "Complete todos los datos del perfil.");
            return;
        }
        try {
            int    edad     = Integer.parseInt(edadStr);
            double estatura = Double.parseDouble(estatStr);

            if (visitante == null) {
                visitante = new Visitante(nombre, doc, edad, estatura, null);
                parque.admitirVisitante(visitante);
                info("Registro exitoso", "¡Bienvenido al parque, " + nombre + "!");
            } else {
                visitante.setEdad(edad);
                visitante.setEstatura(estatura);
                info("Perfil actualizado", "Los datos fueron guardados correctamente.");
            }
            cargarPerfilVisitante();

        } catch (NumberFormatException ex) {
            alerta("Error de formato", "Edad (entero) y Estatura (decimal) deben ser números.");
        }
    }

    @FXML
    void onRecargarSaldo(ActionEvent event) {
        if (visitante == null) {
            alerta("Sin perfil", "Primero guarde su perfil en la pestaña 'Mi Perfil'.");
            return;
        }
        String montoStr = txtMontoCarga.getText().trim();
        if (montoStr.isEmpty()) { alerta("Campo vacío", "Ingrese el monto a recargar."); return; }
        try {
            double monto = Double.parseDouble(montoStr);
            if (monto <= 0) { alerta("Monto inválido", "El monto debe ser mayor que cero."); return; }
            visitante.recargarSaldo(monto);
            lblSaldo.setText(String.format("%.2f", visitante.getSaldoVirtual()));
            txtMontoCarga.clear();
            info("Saldo recargado", "Se agregaron $" + monto + " a su saldo virtual.");
        } catch (NumberFormatException ex) {
            alerta("Error", "El monto debe ser un número.");
        }
    }


    @FXML
    void onSeleccionarTicket(ActionEvent event) {
        boolean familiar = "Familiar".equals(cmbTipoTicket.getValue());
        panelFamiliar.setVisible(familiar);
        panelFamiliar.setManaged(familiar);
        lblCostoFinal.setText("—");
    }

    @FXML
    void onComprarTicket(ActionEvent event) {
        if (visitante == null) {
            alerta("Sin perfil", "Primero registre su perfil en la pestaña 'Mi Perfil'.");
            return;
        }
        String tipo     = cmbTipoTicket.getValue();
        String precioStr = txtPrecioBase.getText().trim();
        if (tipo == null || precioStr.isEmpty()) {
            alerta("Datos incompletos", "Seleccione tipo de ticket e ingrese el precio base.");
            return;
        }
        try {
            double precioBase = Double.parseDouble(precioStr);
            String idTicket   = "TKT-" + System.currentTimeMillis();
            Ticket ticket;

            switch (tipo) {
                case "Familiar" -> {
                    String numStr = txtNumIntegrantes.getText().trim();
                    int num = numStr.isEmpty() ? 1 : Integer.parseInt(numStr);
                    ticket = new TicketFamiliar(idTicket, precioBase, num);
                }
                case "Fast-Pass" -> ticket = new TicketFastPass(idTicket, precioBase);
                default          -> ticket = new TicketGeneral(idTicket, precioBase);
            }

            double costoFinal = ticket.calcularCostoFinal();
            lblCostoFinal.setText("$" + String.format("%.2f", costoFinal));

            if (!visitante.descontarSaldo(costoFinal)) {
                alerta("Saldo insuficiente",
                        "Saldo actual: $" + String.format("%.2f", visitante.getSaldoVirtual())
                                + "\nCosto requerido: $" + String.format("%.2f", costoFinal));
                return;
            }
            visitante.comprarTicket(ticket);
            lblSaldo.setText(String.format("%.2f", visitante.getSaldoVirtual()));
            actualizarInfoTicket();
            lblResultadoTicket.setText("¡Ticket " + tipo + " adquirido correctamente!");
            info("Compra exitosa",
                    "Ticket adquirido.\nTipo: " + tipo
                            + "\nCosto: $" + String.format("%.2f", costoFinal));

        } catch (NumberFormatException ex) {
            alerta("Error de formato", "Verifique los campos numéricos.");
        }
    }


    @FXML
    void onRefrescarAtracciones(ActionEvent event) {
        cargarAtracciones();
        tblAtracciones.refresh();
    }

    @FXML
    void onAgregarFavorito(ActionEvent event) {
        if (visitante == null) {
            alerta("Sin perfil", "Primero registre o inicie sesión con su perfil.");
            return;
        }
        Atraccion sel = tblAtracciones.getSelectionModel().getSelectedItem();
        if (sel == null) { alerta("Sin selección", "Seleccione una atracción de la tabla."); return; }
        visitante.agregarFavorito(sel);
        cargarFavoritos();
        info("Favorito agregado", sel.getNombre() + " fue agregada a tus favoritos.");
    }


    @FXML
    void onEliminarFavorito(ActionEvent event) {
        if (visitante == null) return;
        Atraccion sel = tblFavoritos.getSelectionModel().getSelectedItem();
        if (sel == null) { alerta("Sin selección", "Seleccione una atracción de la lista."); return; }
        visitante.getListaFavoritos().remove(sel);
        cargarFavoritos();
    }

    @FXML
    void onRefrescarFavoritos(ActionEvent event) {
        cargarFavoritos();
        cargarAtracciones();
    }


    private void alerta(String titulo, String msg) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle(titulo); a.setHeaderText(null); a.setContentText(msg); a.showAndWait();
    }

    private void info(String titulo, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(titulo); a.setHeaderText(null); a.setContentText(msg); a.showAndWait();
    }

    @FXML
    void onVolverLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/co/edu/uniquindio/techparkuq/vista/Login.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Tech-Park UQ — Inicio");
            stage.show();
            ((Stage) lblNombreVisitante.getScene().getWindow()).close();
        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Error: " + e.getMessage());
            a.showAndWait();
        }
    }

    @FXML
    void onVerNotificaciones(ActionEvent event) {
        if (visitante == null) { alerta("Sin perfil", "Primero registre su perfil."); return; }
        var notifs = visitante.getNotificaciones();
        if (notifs.isEmpty()) {
            info("Notificaciones", "No tienes notificaciones.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (String n : notifs) sb.append("• ").append(n).append("\n");
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Mis Notificaciones");
        a.setHeaderText("Notificaciones recibidas:");
        TextArea ta = new TextArea(sb.toString());
        ta.setEditable(false); ta.setWrapText(true);
        ta.setPrefRowCount(10);
        a.getDialogPane().setContent(ta);
        a.showAndWait();
    }

}