package co.edu.uniquindio.techparkuq.controlador;

import co.edu.uniquindio.techparkuq.modelo.*;
import co.edu.uniquindio.techparkuq.modelo.abstractas.Atraccion;
import co.edu.uniquindio.techparkuq.modelo.abstractas.Empleado;
import co.edu.uniquindio.techparkuq.modelo.enums.AlertaClimatica;
import co.edu.uniquindio.techparkuq.modelo.enums.EstadoAtraccion;
import co.edu.uniquindio.techparkuq.modelo.enums.TipoAtraccion;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

public class PanelAdministradorController {

    private final Parque parque = ModelFactoryController.getInstance().getParque();

    @FXML private TableView<Empleado> tblEmpleados;
    @FXML private TableColumn<Empleado, String> colEmpNombre;
    @FXML private TableColumn<Empleado, String> colEmpDocumento;
    @FXML private TableColumn<Empleado, Integer> colEmpEdad;
    @FXML private TableColumn<Empleado, String> colEmpRol;
    @FXML private TableColumn<Empleado, String> colEmpZona;
    @FXML private TextField txtEmpNombre;
    @FXML private TextField txtEmpDocumento;
    @FXML private TextField txtEmpEdad;

    @FXML private TableView<Zona> tblZonas;
    @FXML private TableColumn<Zona, String> colZonaNombre;
    @FXML private TableColumn<Zona, Integer> colZonaCapacidad;
    @FXML private TableColumn<Zona, Integer> colZonaNumAtr;
    @FXML private TableColumn<Zona, Integer> colZonaNumOp;
    @FXML private TextField txtZonaNombre;
    @FXML private TextField txtZonaCapacidad;

    @FXML private TableView<Atraccion> tblAtracciones;
    @FXML private TableColumn<Atraccion, String> colAtrId;
    @FXML private TableColumn<Atraccion, String> colAtrNombre;
    @FXML private TableColumn<Atraccion, String> colAtrTipo;
    @FXML private TableColumn<Atraccion, String> colAtrZona;
    @FXML private TableColumn<Atraccion, String> colAtrEstado;
    @FXML private TableColumn<Atraccion, Integer> colAtrContador;
    @FXML private TextField txtAtrId;
    @FXML private TextField txtAtrNombre;
    @FXML private ComboBox<TipoAtraccion> cmbAtrTipo;
    @FXML private ComboBox<String> cmbAtrZona;
    @FXML private TextField txtAtrCapacidad;
    @FXML private TextField txtAtrAlturaMin;
    @FXML private TextField txtAtrEdadMin;
    @FXML private TextField txtAtrCosto;

    @FXML private ComboBox<Operador> cmbAsigOperador;
    @FXML private ComboBox<Zona> cmbAsigZona;
    @FXML private TableView<Operador> tblAsignaciones;
    @FXML private TableColumn<Operador, String> colAsigOperador;
    @FXML private TableColumn<Operador, String> colAsigDoc;
    @FXML private TableColumn<Operador, String> colAsigZona;

    @FXML private Label lblEstadoClima;
    @FXML private ComboBox<AlertaClimatica> cmbClima;
    @FXML private TextArea areaReporte;

    @FXML
    void initialize() {
        configurarTablaEmpleados();
        configurarTablaZonas();
        configurarTablaAtracciones();
        configurarTablaAsignaciones();
        configurarCombos();
        cargarTodos();

        tblEmpleados.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtEmpNombre.setText(newSelection.getNombre());
                txtEmpDocumento.setText(newSelection.getDocumento());
                txtEmpEdad.setText(String.valueOf(newSelection.getEdad()));
                txtEmpDocumento.setEditable(false);
            }
        });

        tblZonas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtZonaNombre.setText(newSelection.getNombre());
                txtZonaCapacidad.setText(String.valueOf(newSelection.getCapacidadMaxima()));
            }
        });

        tblAtracciones.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtAtrId.setText(newSelection.getIdUnico());
                txtAtrNombre.setText(newSelection.getNombre());
                txtAtrCapacidad.setText(String.valueOf(newSelection.getCapacidadMaximaCiclo()));
                txtAtrAlturaMin.setText(String.valueOf(newSelection.getAlturaMinima()));
                txtAtrEdadMin.setText(String.valueOf(newSelection.getEdadMinima()));
                txtAtrCosto.setText(String.valueOf(newSelection.getCostoAdicional()));
                cmbAtrZona.setValue(encontrarZonaDe(newSelection));
                txtAtrId.setEditable(false);
                if (newSelection instanceof AtraccionAcuatica) {
                    cmbAtrTipo.setValue(TipoAtraccion.ACUATICA);
                } else if (newSelection instanceof AtraccionMecanicaAltura) {
                    cmbAtrTipo.setValue(TipoAtraccion.MECANICA_ALTURA);
                } else if (newSelection instanceof AtraccionShow) {
                    cmbAtrTipo.setValue(TipoAtraccion.SHOW);
                } else if (newSelection instanceof AtraccionGeneral) {
                    cmbAtrTipo.setValue(TipoAtraccion.FAMILIAR);
                }
            }
        });
    }

    private void refrescarUI() {
        tblEmpleados.refresh();
        tblZonas.refresh();
        tblAtracciones.refresh();
        tblAsignaciones.refresh();
    }

    private void configurarTablaEmpleados() {
        colEmpNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        colEmpDocumento.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDocumento()));
        colEmpEdad.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getEdad()).asObject());
        colEmpRol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getRol()));
        colEmpZona.setCellValueFactory(c -> {
            Empleado e = c.getValue();
            if (e instanceof Operador op) {
                Zona z = op.getZonaAsignada();
                return new SimpleStringProperty(z != null ? z.getNombre() : "Sin asignar");
            }
            return new SimpleStringProperty("—");
        });
    }

    private void configurarTablaZonas() {
        colZonaNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        colZonaCapacidad.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getCapacidadMaxima()).asObject());
        colZonaNumAtr.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getListaAtracciones().size()).asObject());
        colZonaNumOp.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getListOperadores().size()).asObject());
    }

    private void configurarTablaAtracciones() {
        colAtrId.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getIdUnico()));
        colAtrNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        colAtrTipo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getClass().getSimpleName()));
        colAtrZona.setCellValueFactory(c -> new SimpleStringProperty(encontrarZonaDe(c.getValue())));
        colAtrEstado.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEstado().toString()));
        colAtrContador.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getContadorUso()).asObject());
    }

    private void configurarTablaAsignaciones() {
        colAsigOperador.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        colAsigDoc.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDocumento()));
        colAsigZona.setCellValueFactory(c -> {
            Zona z = c.getValue().getZonaAsignada();
            return new SimpleStringProperty(z != null ? z.getNombre() : "Sin asignar");
        });
    }

    private void configurarCombos() {
        cmbAtrTipo.setItems(FXCollections.observableArrayList(TipoAtraccion.values()));
        cmbClima.setItems(FXCollections.observableArrayList(AlertaClimatica.values()));

        cmbAsigOperador.setConverter(new StringConverter<>() {
            @Override public String toString(Operador op) { return op != null ? op.getNombre() + " (" + op.getDocumento() + ")" : ""; }
            @Override public Operador fromString(String s) { return null; }
        });

        cmbAsigZona.setConverter(new StringConverter<>() {
            @Override public String toString(Zona z) { return z != null ? z.getNombre() : ""; }
            @Override public Zona fromString(String s) { return null; }
        });

        actualizarCombosZona();
        actualizarCombosOperador();
    }

    private void cargarTodos() {
        cargarEmpleados();
        cargarZonas();
        cargarAtracciones();
        cargarAsignaciones();
        actualizarEtiquetaClima();
    }

    private void cargarEmpleados() {
        tblEmpleados.setItems(FXCollections.observableArrayList(parque.getListaEmpleados()));
    }

    private void cargarZonas() {
        tblZonas.setItems(FXCollections.observableArrayList(parque.getListaZonas()));
        actualizarCombosZona();
    }

    private void cargarAtracciones() {
        tblAtracciones.setItems(FXCollections.observableArrayList(parque.getListaAtracciones()));
        actualizarCombosZona();
    }

    private void cargarAsignaciones() {
        ObservableList<Operador> ops = FXCollections.observableArrayList();
        for (Empleado e : parque.getListaEmpleados()) {
            if (e instanceof Operador op) ops.add(op);
        }
        tblAsignaciones.setItems(ops);
        actualizarCombosOperador();
    }

    private void actualizarCombosZona() {
        ObservableList<String> nombres = FXCollections.observableArrayList();
        for (Zona z : parque.getListaZonas()) nombres.add(z.getNombre());
        cmbAtrZona.setItems(nombres);
        cmbAsigZona.setItems(FXCollections.observableArrayList(parque.getListaZonas()));
    }

    private void actualizarCombosOperador() {
        ObservableList<Operador> ops = FXCollections.observableArrayList();
        for (Empleado e : parque.getListaEmpleados()) {
            if (e instanceof Operador op) ops.add(op);
        }
        cmbAsigOperador.setItems(ops);
    }

    private void actualizarEtiquetaClima() {
        AlertaClimatica clima = parque.getEstadoClima();
        lblEstadoClima.setText(clima.toString());
        lblEstadoClima.setStyle(switch (clima) {
            case NINGUNA -> "-fx-font-weight: bold; -fx-text-fill: #2E7D32; -fx-font-size: 14;";
            case LLUVIA_FUERTE -> "-fx-font-weight: bold; -fx-text-fill: #1565C0; -fx-font-size: 14;";
            case TORMENTA_ELECTRICA -> "-fx-font-weight: bold; -fx-text-fill: #c62828; -fx-font-size: 14;";
        });
    }

    @FXML
    void onContratarEmpleado(ActionEvent event) {
        String nombre = txtEmpNombre.getText().trim();
        String doc = txtEmpDocumento.getText().trim();
        String edadStr = txtEmpEdad.getText().trim();

        if (nombre.isEmpty() || doc.isEmpty() || edadStr.isEmpty()) {
            alerta("Campos incompletos", "Complete todos los campos del formulario.");
            return;
        }

        for (Empleado e : parque.getListaEmpleados()) {
            if (e.getDocumento().equals(doc)) {
                alerta("Documento duplicado", "Ya existe un empleado registrado con el documento: " + doc);
                return;
            }
        }

        try {
            int edad = Integer.parseInt(edadStr);
            parque.contratarEmpleado(new Operador(nombre, doc, edad));
            ModelFactoryController.getInstance().guardarDatosSerializable();
            cargarEmpleados();
            actualizarCombosOperador();
            limpiarFormEmp();
            refrescarUI();
            info("Operador contratado", nombre + " registrado en el sistema.");
        } catch (NumberFormatException ex) {
            alerta("Error", "La edad debe ser un número entero.");
        }
    }

    @FXML
    void onActualizarEmpleado(ActionEvent event) {
        Empleado sel = tblEmpleados.getSelectionModel().getSelectedItem();
        if (sel == null) {
            alerta("Sin selección", "Seleccione un empleado de la tabla para actualizar.");
            return;
        }
        String nombre = txtEmpNombre.getText().trim();
        String edadStr = txtEmpEdad.getText().trim();

        if (nombre.isEmpty() || edadStr.isEmpty()) {
            alerta("Campos incompletos", "Complete los campos de nombre y edad para actualizar.");
            return;
        }
        try {
            int edad = Integer.parseInt(edadStr);
            sel.setNombre(nombre);
            sel.setEdad(edad);
            ModelFactoryController.getInstance().guardarDatosSerializable();
            actualizarCombosOperador();
            refrescarUI();
            limpiarFormEmp();
            info("Éxito", "Datos del empleado actualizados.");
        } catch (NumberFormatException ex) {
            alerta("Error", "La edad debe ser un número entero.");
        }
    }

    @FXML
    void onDesvincularEmpleado(ActionEvent event) {
        Empleado sel = tblEmpleados.getSelectionModel().getSelectedItem();
        if (sel == null) { alerta("Sin selección", "Seleccione un empleado de la tabla."); return; }
        if (sel instanceof Operador op && op.getZonaAsignada() != null) {
            op.getZonaAsignada().getListOperadores().remove(op);
        }
        parque.getListaEmpleados().remove(sel);
        ModelFactoryController.getInstance().guardarDatosSerializable();
        cargarEmpleados();
        cargarAsignaciones();
        actualizarCombosOperador();
        refrescarUI();
        limpiarFormEmp();
        info("Éxito", "Empleado desvinculado del sistema.");
    }

    @FXML
    void onCrearZona(ActionEvent event) {
        String nombre = txtZonaNombre.getText().trim();
        String capStr = txtZonaCapacidad.getText().trim();
        if (nombre.isEmpty() || capStr.isEmpty()) {
            alerta("Campos incompletos", "Ingrese nombre y capacidad.");
            return;
        }
        try {
            parque.crearZona(new Zona(nombre, Integer.parseInt(capStr)));
            ModelFactoryController.getInstance().guardarDatosSerializable();
            cargarZonas();
            cargarAtracciones();
            actualizarCombosZona();
            refrescarUI();
            limpiarFormZona();
            info("Zona creada", "La zona '" + nombre + "' fue registrada.");
        } catch (NumberFormatException ex) {
            alerta("Error", "La capacidad debe ser un número entero.");
        }
    }

    @FXML
    void onActualizarZona(ActionEvent event) {
        Zona sel = tblZonas.getSelectionModel().getSelectedItem();
        if (sel == null) {
            alerta("Sin selección", "Seleccione una zona de la tabla para actualizar.");
            return;
        }
        String nombre = txtZonaNombre.getText().trim();
        String capStr = txtZonaCapacidad.getText().trim();

        if (nombre.isEmpty() || capStr.isEmpty()) {
            alerta("Campos incompletos", "Complete los campos para actualizar la zona.");
            return;
        }
        try {
            int cap = Integer.parseInt(capStr);
            sel.setNombre(nombre);
            sel.setCapacidadMaxima(cap);
            ModelFactoryController.getInstance().guardarDatosSerializable();
            actualizarCombosZona();
            refrescarUI();
            limpiarFormZona();
            info("Éxito", "Zona actualizada correctamente.");
        } catch (NumberFormatException ex) {
            alerta("Error", "La capacidad debe ser un número entero.");
        }
    }

    @FXML
    void onEliminarZona(ActionEvent event) {
        Zona sel = tblZonas.getSelectionModel().getSelectedItem();
        if (sel == null) { alerta("Sin selección", "Seleccione una zona de la tabla."); return; }
        if (!sel.getListaAtracciones().isEmpty()) {
            alerta("Zona ocupada", "No se puede eliminar una zona con atracciones asociadas.");
            return;
        }
        parque.getListaZonas().remove(sel);
        ModelFactoryController.getInstance().guardarDatosSerializable();
        cargarZonas();
        cargarAtracciones();
        actualizarCombosZona();
        refrescarUI();
        limpiarFormZona();
        info("Éxito", "Zona Frame eliminada.");
    }

    @FXML
    void onCrearAtraccion(ActionEvent event) {
        String id = txtAtrId.getText().trim();
        String nombre = txtAtrNombre.getText().trim();
        TipoAtraccion tipo = cmbAtrTipo.getValue();
        String zonaNombre = cmbAtrZona.getValue();
        String capStr = txtAtrCapacity();

        if (id.isEmpty() || nombre.isEmpty() || tipo == null || zonaNombre == null || capStr.isEmpty()) {
            alerta("Campos incompletos", "Complete: ID, Nombre, Tipo, Zona y Capacidad.");
            return;
        }

        for (Atraccion a : parque.getListaAtracciones()) {
            if (a.getIdUnico().equals(id)) {
                alerta("ID duplicado", "Ya existe una atracción registrada con el ID único: " + id);
                return;
            }
        }

        try {
            int cap = Integer.parseInt(capStr);
            double altMin = txtAtrAlturaMin.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(txtAtrAlturaMin.getText().trim());
            int edMin = txtAtrEdadMin.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtAtrEdadMin.getText().trim());
            double costo = txtAtrCosto.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(txtAtrCosto.getText().trim());

            Atraccion nueva = crearPorTipo(tipo, id, nombre, cap, altMin, edMin, costo);
            parque.crearAtraccion(nueva, zonaNombre);
            ModelFactoryController.getInstance().guardarDatosSerializable();
            cargarAtracciones();
            cargarZonas();
            actualizarCombosZona();
            refrescarUI();
            limpiarFormAtr();
            info("Atracción creada", "'" + nombre + "' registrada en " + zonaNombre + ".");
        } catch (NumberFormatException ex) {
            alerta("Error de formato", "Revise los campos numéricos.");
        }
    }

    private String txtAtrCapacity() {
        return txtAtrCapacidad.getText().trim();
    }

    @FXML
    void onActualizarAtraccion(ActionEvent event) {
        Atraccion sel = tblAtracciones.getSelectionModel().getSelectedItem();
        if (sel == null) {
            alerta("Sin selección", "Seleccione una atracción de la tabla para actualizar.");
            return;
        }
        String nombre = txtAtrNombre.getText().trim();
        String zonaNombre = cmbAtrZona.getValue();
        String capStr = txtAtrCapacity();

        if (nombre.isEmpty() || zonaNombre == null || capStr.isEmpty()) {
            alerta("Campos incompletos", "Complete Nombre, Zona y Capacidad para actualizar.");
            return;
        }
        try {
            int cap = Integer.parseInt(capStr);
            double altMin = Double.parseDouble(txtAtrAlturaMin.getText().trim());
            int edMin = Integer.parseInt(txtAtrEdadMin.getText().trim());
            double costo = Double.parseDouble(txtAtrCosto.getText().trim());

            String zonaActualNombre = encontrarZonaDe(sel);
            if (!zonaActualNombre.equals(zonaNombre)) {
                for (Zona z : parque.getListaZonas()) {
                    if (z.getNombre().equals(zonaActualNombre)) {
                        z.getListaAtracciones().remove(sel);
                    }
                }
                for (Zona z : parque.getListaZonas()) {
                    if (z.getNombre().equals(zonaNombre)) {
                        z.getListaAtracciones().add(sel);
                    }
                }
            }

            sel.setNombre(nombre);
            sel.setCapacidadMaximaCiclo(cap);
            sel.setAlturaMinima(altMin);
            sel.setEdadMinima(edMin);
            sel.setCostoAdicional(costo);
            ModelFactoryController.getInstance().guardarDatosSerializable();
            actualizarCombosZona();
            refrescarUI();
            limpiarFormAtr();
            info("Éxito", "Atracción actualizada en el sistema.");
        } catch (NumberFormatException ex) {
            alerta("Error de formato", "Revise los campos numéricos.");
        }
    }

    private Atraccion crearPorTipo(TipoAtraccion tipo, String id, String nombre, int cap, double altMin, int edMin, double costo) {
        return switch (tipo) {
            case ACUATICA -> new AtraccionAcuatica(id, nombre, cap, altMin, edMin, costo, false);
            case MECANICA_ALTURA -> new AtraccionMecanicaAltura(id, nombre, cap, altMin, edMin, costo, 80.0);
            case SHOW -> new AtraccionShow(id, nombre, cap, altMin, edMin, costo, "15:00");
            case FAMILIAR -> new AtraccionGeneral(id, nombre, cap, altMin, edMin, costo);
        };
    }

    @FXML
    void onDeshabilitarAtraccion(ActionEvent event) {
        Atraccion sel = tblAtracciones.getSelectionModel().getSelectedItem();
        if (sel == null) { alerta("Sin selección", "Seleccione una atracción."); return; }
        parque.eliminarAtraccion(sel.getIdUnico());
        ModelFactoryController.getInstance().guardarDatosSerializable();
        actualizarCombosZona();
        refrescarUI();
        limpiarFormAtr();
        info("Atracción deshabilitada", "Estado cambiado a CERRADA.");
    }

    @FXML
    void onAsignarOperador(ActionEvent event) {
        Operador op = cmbAsigOperador.getValue();
        Zona zona = cmbAsigZona.getValue();
        if (op == null || zona == null) {
            alerta("Selección incompleta", "Seleccione operador y zona.");
            return;
        }
        if (op.getZonaAsignada() != null) {
            op.getZonaAsignada().getListOperadores().remove(op);
        }
        op.setZonaAsignada(zona);
        zona.getListOperadores().add(op);
        op.getListAtraccionesGestionadas().clear();
        op.getListAtraccionesGestionadas().addAll(zona.getListaAtracciones());
        ModelFactoryController.getInstance().guardarDatosSerializable();
        actualizarCombosOperador();
        refrescarUI();
        info("Asignación exitosa", op.getNombre() + " asignado a " + zona.getNombre() + ".");
    }

    @FXML
    void onDesdesasignarOperador(ActionEvent event) {
        Operador op = tblAsignaciones.getSelectionModel().getSelectedItem();
        if (op == null) op = cmbAsigOperador.getValue();
        if (op == null) {
            alerta("Sin selección", "Seleccione un operador de la tabla.");
            return;
        }
        Zona zona = op.getZonaAsignada();
        if (zona == null) {
            alerta("Sin asignación", "El operador seleccionado no tiene ninguna zona asignada.");
            return;
        }
        zona.getListOperadores().remove(op);
        op.setZonaAsignada(null);
        op.getListAtraccionesGestionadas().clear();
        ModelFactoryController.getInstance().guardarDatosSerializable();
        actualizarCombosOperador();
        refrescarUI();
        info("Desasignación exitosa", op.getNombre() + " desvinculado.");
    }

    @FXML
    void onActivarClima(ActionEvent event) {
        AlertaClimatica alerta = cmbClima.getValue();
        if (alerta == null) { alerta("Sin selección", "Seleccione un tipo de alerta."); return; }
        parque.cambiarEstadoClima(alerta);
        ModelFactoryController.getInstance().guardarDatosSerializable();
        actualizarEtiquetaClima();
        refrescarUI();
        info("Alerta activada", "Clima cambiado a: " + alerta);
    }

    @FXML
    void onGenerarReporte(ActionEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append("====== REPORTE GENERAL — TECH-PARK UQ ======\n\n");
        sb.append("Parque : ").append(parque.getNombre()).append("\n");
        sb.append("Visitantes : ").append(parque.getListaVisitantes().size()).append("\n");
        sb.append("Empleados : ").append(parque.getListaEmpleados().size()).append("\n");
        sb.append("Zonas : ").append(parque.getListaZonas().size()).append("\n");
        sb.append("Atracciones : ").append(parque.getListaAtracciones().size()).append("\n");
        sb.append("Clima actual : ").append(parque.getEstadoClima()).append("\n");
        sb.append("\n------ DETALLE POR ZONA ------\n");

        for (Zona z : parque.getListaZonas()) {
            sb.append("\n[").append(z.getNombre()).append("]\n");
            for (Atraccion a : z.getListaAtracciones()) {
                sb.append(" • ").append(a.getNombre()).append(" [").append(a.getEstado()).append("]\n");
            }
        }

        sb.append("\n\n").append(parque.generarReporteEstadistico());

        areaReporte.setText(sb.toString());
    }

    private String encontrarZonaDe(Atraccion a) {
        for (Zona z : parque.getListaZonas()) {
            if (z.getListaAtracciones().contains(a)) return z.getNombre();
        }
        return "Sin zona";
    }

    private void limpiarFormEmp() {
        txtEmpNombre.clear();
        txtEmpDocumento.clear();
        txtEmpEdad.clear();
        txtEmpDocumento.setEditable(true);
    }

    private void limpiarFormZona() { txtZonaNombre.clear(); txtZonaCapacidad.clear(); }

    private void limpiarFormAtr() {
        txtAtrId.clear(); txtAtrNombre.clear(); txtAtrCapacidad.clear();
        txtAtrAlturaMin.clear(); txtAtrEdadMin.clear(); txtAtrCosto.clear();
        cmbAtrTipo.setValue(null); cmbAtrZona.setValue(null);
        txtAtrId.setEditable(true);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/techparkuq/vista/Login.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Tech-Park UQ — Inicio");
            stage.show();
            ((Stage) tblEmpleados.getScene().getWindow()).close();
        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("No se pudo volver al login: " + e.getMessage());
            a.showAndWait();
        }
    }
    @FXML
    void onVerEstadisticas(ActionEvent event) {
        String stats = parque.generarReporteEstadistico();
        areaReporte.setText(stats);
    }
}