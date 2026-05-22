package co.edu.uniquindio.techparkuq.controlador;

import co.edu.uniquindio.techparkuq.modelo.abstractas.Atraccion;
import co.edu.uniquindio.techparkuq.modelo.Parque;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class GestionAtraccionesController {

    private final ModelFactoryController mfc;
    private final Parque parque;
    private ObservableList<Atraccion> listaAtracciones;

    @FXML
    private Button btnActualizar;

    @FXML
    private TableColumn<Atraccion, Integer> colContadorUso;

    @FXML
    private TableColumn<Atraccion, String> colEstado;

    @FXML
    private TableColumn<Atraccion, String> colId;

    @FXML
    private TableColumn<Atraccion, String> colNombre;

    @FXML
    private TableView<Atraccion> tblAtracciones;

    public GestionAtraccionesController() {
        this.mfc = ModelFactoryController.getInstance();
        this.parque = mfc.getParque();
    }

    @FXML
    void initialize() {
        validarInyecciones();
        configurarColumnas();
        cargarDatosTabla();
    }

    private void configurarColumnas() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colContadorUso.setCellValueFactory(new PropertyValueFactory<>("contadorUso"));
    }

    private void cargarDatosTabla() {
        try {
            if (parque.getListaAtracciones() != null) {
                listaAtracciones = FXCollections.observableArrayList(parque.getListaAtracciones());
                tblAtracciones.setItems(listaAtracciones);
                tblAtracciones.refresh();
            }
        } catch (Exception e) {
            System.err.println("CRÍTICO: Error al sincronizar el TableView de atracciones: " + e.getMessage());
        }
    }

    @FXML
    void onActualizarClick(ActionEvent event) {
        System.out.println("UI_LOG: Solicitando refresco de estados de atracciones.");
        cargarDatosTabla();
    }

    private void validarInyecciones() {
        assert btnActualizar != null : "fx:id=\"btnActualizar\" was not injected: check your FXML file.";
        assert colContadorUso != null : "fx:id=\"colContadorUso\" was not injected: check your FXML file.";
        assert colEstado != null : "fx:id=\"colEstado\" was not injected: check your FXML file.";
        assert colId != null : "fx:id=\"colId\" was not injected: check your FXML file.";
        assert colNombre != null : "fx:id=\"colNombre\" was not injected: check your FXML file.";
        assert tblAtracciones != null : "fx:id=\"tblAtracciones\" was not injected: check your FXML file.";
    }
}