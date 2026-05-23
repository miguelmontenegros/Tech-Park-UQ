package co.edu.uniquindio.techparkuq.controlador;

import co.edu.uniquindio.techparkuq.modelo.*;
import co.edu.uniquindio.techparkuq.modelo.abstractas.Atraccion;
import co.edu.uniquindio.techparkuq.modelo.abstractas.Ticket;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;

public class PanelVisitanteController {

    private final Parque parque = ModelFactoryController.getInstance().getParque();
    private Visitante visitante;

    private static final double PRECIO_GENERAL   = 35_000;
    private static final double PRECIO_FAMILIAR  = 28_000;
    private static final double PRECIO_FASTPASS  = 55_000;


    @FXML private Label lblNombreVisitante;
    @FXML private TextField txtNombre;
    @FXML private TextField txtDocumento;
    @FXML private TextField txtEdad;
    @FXML private TextField txtEstatura;
    @FXML private Label lblSaldo;
    @FXML private TextField txtMontoCarga;
    @FXML private Label lblTicketInfo;

    @FXML private ComboBox<String> cmbTipoTicket;
    @FXML private HBox panelFamiliar;
    @FXML private TextField txtNumIntegrantes;
    @FXML private Label lblPrecioBase;
    @FXML private Label lblCostoFinal;
    @FXML private Label lblResultadoTicket;

    @FXML private TableView<Atraccion> tblAtracciones;
    @FXML private TableColumn<Atraccion, String> colVisNombre;
    @FXML private TableColumn<Atraccion, String> colVisTipo;
    @FXML private TableColumn<Atraccion, String> colVisEstado;
    @FXML private TableColumn<Atraccion, Integer> colVisEspera;
    @FXML private TableColumn<Atraccion, Double> colVisCosto;
    @FXML private TableColumn<Atraccion, Double> colVisAlt;

    @FXML private TableView<Atraccion> tblFavoritos;
    @FXML private TableColumn<Atraccion, String> colFavNombre;
    @FXML private TableColumn<Atraccion, String> colFavTipo;
    @FXML private TableColumn<Atraccion, String> colFavEstado;

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
        colVisNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        colVisTipo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getClass().getSimpleName()));
        colVisEstado.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEstado().toString()));
        colVisEspera.setCellValueFactory(c -> {
            if (visitante != null) {
                return new SimpleIntegerProperty(c.getValue().getTiempoEsperaParaVisitante(visitante)).asObject();
            }
            return new SimpleIntegerProperty(c.getValue().getTiempoEsperaEstimado()).asObject();
        });
        colVisCosto.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getCostoAdicional()).asObject());
        colVisAlt.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getAlturaMinima()).asObject());

        colFavNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        colFavTipo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getClass().getSimpleName()));
        colFavEstado.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEstado().toString()));
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
            lblTicketInfo.setText(t.getClass().getSimpleName() + " | ID: " + t.getIdTicket() + " | Costo total: $" + String.format("%.2f", t.calcularCostoFinal()));
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
        String nombre = txtNombre.getText().trim();
        String doc = txtDocumento.getText().trim();
        String edadStr = txtEdad.getText().trim();
        String estatStr = txtEstatura.getText().trim();

        if (nombre.isEmpty() || doc.isEmpty() || edadStr.isEmpty() || estatStr.isEmpty()) {
            alerta("Campos incompletos", "Complete todos los datos del perfil.");
            return;
        }
        try {
            int edad = Integer.parseInt(edadStr);
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
            alerta("Sin perfil", "Primero guarde su perfil en la pestana Mi Perfil.");
            return;
        }
        String montoStr = txtMontoCarga.getText().trim();
        if (montoStr.isEmpty()) { alerta("Campo vacio", "Ingrese el monto a recargar."); return; }

        double monto;
        try {
            monto = Double.parseDouble(montoStr);
            if (monto <= 0) { alerta("Monto invalido", "El monto debe ser mayor que cero."); return; }
        } catch (NumberFormatException ex) {
            alerta("Error", "El monto debe ser un numero valido.");
            return;
        }

        abrirDialogoPago(monto);
    }

    private void abrirDialogoPago(double monto) {
        Stage dialog = new Stage();
        dialog.setTitle("Pago con Tarjeta");
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(lblNombreVisitante.getScene().getWindow());
        dialog.setResizable(false);

        Label lblTitulo = new Label("Pago Seguro con Tarjeta");
        lblTitulo.setFont(Font.font("System", FontWeight.BOLD, 15));
        lblTitulo.setTextFill(Color.WHITE);

        Label lblSub = new Label("Ingrese los datos de su tarjeta para continuar");
        lblSub.setFont(Font.font("System", 11));
        lblSub.setTextFill(Color.web("#A5D6A7"));

        VBox header = new VBox(4, lblTitulo, lblSub);
        header.setPadding(new Insets(16, 20, 16, 20));
        header.setStyle("-fx-background-color: #00542E;");

        Label lblMontoTitulo = new Label("Monto a recargar");
        lblMontoTitulo.setFont(Font.font("System", FontWeight.BOLD, 12));
        lblMontoTitulo.setTextFill(Color.web("#555"));

        Label lblDolar = new Label("$");
        lblDolar.setFont(Font.font("System", FontWeight.BOLD, 18));
        lblDolar.setTextFill(Color.web("#00542E"));

        Label lblMonto = new Label(String.format("%,.2f", monto));
        lblMonto.setFont(Font.font("System", FontWeight.BOLD, 22));
        lblMonto.setTextFill(Color.web("#00542E"));

        HBox montoBox = new HBox(6, lblDolar, lblMonto);
        montoBox.setAlignment(Pos.CENTER_LEFT);
        montoBox.setPadding(new Insets(8, 12, 8, 12));
        montoBox.setStyle("-fx-background-color: #e8f5e9; -fx-border-color: #00542E; -fx-border-radius: 5; -fx-background-radius: 5;");

        TextField txtNum = new TextField();
        txtNum.setPromptText("1234  5678  9012  3456");
        txtNum.setFont(Font.font("System", 14));
        txtNum.setPrefWidth(370);
        txtNum.setStyle("-fx-padding: 8; -fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-radius: 5;");

        txtNum.textProperty().addListener((obs, v, n) -> {
            String d = n.replaceAll("[^\\d]", "");
            if (d.length() > 16) d = d.substring(0, 16);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < d.length(); i++) {
                if (i > 0 && i % 4 == 0) sb.append("  ");
                sb.append(d.charAt(i));
            }
            String r = sb.toString();
            if (!r.equals(n)) { txtNum.setText(r); txtNum.positionCaret(r.length()); }
        });

        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Como aparece en la tarjeta");
        txtNombre.setPrefWidth(370);
        txtNombre.setStyle("-fx-padding: 8; -fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-radius: 5;");

        TextField txtVenc = new TextField();
        txtVenc.setPromptText("MM/AA");
        txtVenc.setPrefWidth(180);
        txtVenc.setStyle("-fx-padding: 8; -fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-radius: 5;");

        txtVenc.textProperty().addListener((obs, v, n) -> {
            String d = n.replaceAll("[^\\d]", "");
            if (d.length() > 4) d = d.substring(0, 4);
            String r = d.length() > 2 ? d.substring(0, 2) + "/" + d.substring(2) : d;
            if (!r.equals(n)) { txtVenc.setText(r); txtVenc.positionCaret(r.length()); }
        });

        TextField txtCvv = new TextField();
        txtCvv.setPromptText("123");
        txtCvv.setPrefWidth(90);
        txtCvv.setStyle("-fx-padding: 8; -fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-radius: 5;");

        txtCvv.textProperty().addListener((obs, v, n) -> {
            String d = n.replaceAll("[^\\d]", "");
            if (d.length() > 4) d = d.substring(0, 4);
            if (!d.equals(n)) { txtCvv.setText(d); txtCvv.positionCaret(d.length()); }
        });

        final String[] CARD_NOMBRES  = {"Visa", "Mastercard", "American Express", "AMEX"};
        final String[] CARD_ARCHIVOS = {"visa", "mastercard", "amex", "amex2"};
        final String[] CARD_BG_UNSEL = {"#f0f4ff", "#fff4f0", "#f0f8ff", "#f0f8ff"};
        final String[] CARD_BG_SEL   = {"#1A1F71", "#cc0000", "#0070BA", "#0070BA"};

        final String[] tipoSeleccionado = {null};
        Button[] btnsTarjeta = new Button[CARD_NOMBRES.length];
        HBox selectorTarjeta = new HBox(10);
        selectorTarjeta.setAlignment(Pos.CENTER_LEFT);

        for (int i = 0; i < CARD_NOMBRES.length; i++) {
            final int idx = i;
            Button btn = new Button();
            btn.setPrefSize(82, 52);
            btn.setStyle("-fx-background-color: " + CARD_BG_UNSEL[i] + "; -fx-border-color: #ccc; -fx-border-radius: 8; -fx-background-radius: 8; -fx-cursor: hand;");
            btn.setEffect(new DropShadow(4, javafx.scene.paint.Color.gray(0, 0.15)));
            try {
                var url = getClass().getResource("/co/edu/uniquindio/techparkuq/vista/img/" + CARD_ARCHIVOS[i] + ".png");
                if (url != null) {
                    javafx.scene.image.ImageView iv = new javafx.scene.image.ImageView(new javafx.scene.image.Image(url.toExternalForm()));
                    iv.setFitWidth(64); iv.setFitHeight(36); iv.setPreserveRatio(true);
                    btn.setGraphic(iv);
                }
            } catch (Exception ignored) {
                btn.setText(CARD_NOMBRES[i]);
            }
            btn.setOnAction(e -> {
                tipoSeleccionado[0] = CARD_NOMBRES[idx];
                for (int j = 0; j < btnsTarjeta.length; j++) {
                    if (j == idx) {
                        btnsTarjeta[j].setStyle("-fx-background-color: " + CARD_BG_SEL[idx] + "; -fx-border-color: " + CARD_BG_SEL[idx] + "; -fx-border-radius: 8; -fx-background-radius: 8; -fx-cursor: hand;");
                        btnsTarjeta[j].setEffect(new DropShadow(8, javafx.scene.paint.Color.gray(0, 0.35)));
                    } else {
                        btnsTarjeta[j].setStyle("-fx-background-color: " + CARD_BG_UNSEL[j] + "; -fx-border-color: #ccc; -fx-border-radius: 8; -fx-background-radius: 8; -fx-cursor: hand;");
                        btnsTarjeta[j].setEffect(new DropShadow(4, javafx.scene.paint.Color.gray(0, 0.15)));
                    }
                }
            });
            btnsTarjeta[i] = btn;
            selectorTarjeta.getChildren().add(btn);
        }

        Label lNum    = campoLabel("Numero de tarjeta");
        Label lNombre = campoLabel("Nombre del titular");
        Label lVenc   = campoLabel("Vencimiento (MM/AA)");
        Label lCvv    = campoLabel("CVV");
        Label lTipo   = campoLabel("Tipo de tarjeta");

        HBox fila1 = new HBox(16,
                new VBox(4, lVenc, txtVenc),
                new VBox(4, lCvv, txtCvv));
        fila1.setAlignment(Pos.CENTER_LEFT);

        Label lblEstado = new Label("Procesando pago...");
        lblEstado.setFont(Font.font("System", FontWeight.BOLD, 12));
        lblEstado.setTextFill(Color.web("#00542E"));

        ProgressBar progress = new ProgressBar();
        progress.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
        progress.setPrefWidth(370);
        progress.setPrefHeight(12);
        progress.setStyle("-fx-accent: #00542E;");

        VBox panelProgreso = new VBox(6, lblEstado, progress);
        panelProgreso.setVisible(false);
        panelProgreso.setManaged(false);

        Label lblMsg = new Label("");
        lblMsg.setWrapText(true);
        lblMsg.setMaxWidth(370);
        lblMsg.setFont(Font.font("System", FontWeight.BOLD, 12));

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle("-fx-background-color: #e0e0e0; -fx-text-fill: #333; -fx-font-weight: bold; -fx-padding: 8 18; -fx-background-radius: 5;");
        btnCancelar.setOnAction(e -> dialog.close());

        Button btnPagar = new Button("Pagar ahora");
        btnPagar.setStyle("-fx-background-color: #E65100; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 20; -fx-background-radius: 5; -fx-font-size: 13;");

        btnPagar.setOnAction(e -> {
            String num = txtNum.getText().replaceAll("\\s", "");
            if (num.length() < 16)          { setError(lblMsg, "Numero de tarjeta invalido (16 digitos)."); return; }
            if (txtNombre.getText().isBlank()){ setError(lblMsg, "Ingrese el nombre del titular."); return; }
            if (!txtVenc.getText().matches("\\d{2}/\\d{2}")) { setError(lblMsg, "Vencimiento invalido, use MM/AA."); return; }
            if (txtCvv.getText().length() < 3){ setError(lblMsg, "CVV invalido (minimo 3 digitos)."); return; }
            if (tipoSeleccionado[0] == null)  { setError(lblMsg, "Seleccione el tipo de tarjeta."); return; }

            try {
                int mes  = Integer.parseInt(txtVenc.getText().substring(0, 2));
                int anio = Integer.parseInt(txtVenc.getText().substring(3)) + 2000;
                java.time.LocalDate hoy = java.time.LocalDate.now();
                if (mes < 1 || mes > 12) { setError(lblMsg, "Mes invalido."); return; }
                if (anio < hoy.getYear() || (anio == hoy.getYear() && mes < hoy.getMonthValue())) {
                    setError(lblMsg, "La tarjeta esta vencida."); return;
                }
            } catch (NumberFormatException ex) { setError(lblMsg, "Fecha invalida."); return; }

            btnPagar.setDisable(true);
            btnCancelar.setDisable(true);
            lblMsg.setText("");
            panelProgreso.setVisible(true);
            panelProgreso.setManaged(true);

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
                        ev -> lblEstado.setText(paso)));
            }
            tl.getKeyFrames().add(new KeyFrame(Duration.millis(750L * (pasos.length + 1)), ev -> {
                panelProgreso.setVisible(false);
                panelProgreso.setManaged(false);
                lblMsg.setTextFill(Color.web("#2E7D32"));
                lblMsg.setText("Pago aprobado! Saldo recargado exitosamente.");

                btnPagar.setText("Cerrar");
                btnPagar.setStyle("-fx-background-color: #2E7D32; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 20; -fx-background-radius: 5; -fx-font-size: 13;");
                btnPagar.setDisable(false);
                btnCancelar.setDisable(false);
                btnCancelar.setVisible(false);
                btnPagar.setOnAction(ev2 -> {
                    visitante.recargarSaldo(monto);
                    lblSaldo.setText(String.format("%.2f", visitante.getSaldoVirtual()));
                    txtMontoCarga.clear();
                    dialog.close();
                });
            }));
            tl.play();
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox footer = new HBox(12, spacer, btnCancelar, btnPagar);
        footer.setAlignment(Pos.CENTER_RIGHT);
        footer.setPadding(new Insets(12, 20, 16, 20));
        footer.setStyle("-fx-background-color: #f4f6f8; -fx-border-color: #ddd; -fx-border-width: 1 0 0 0;");

        VBox body = new VBox(12,
                new VBox(4, lblMontoTitulo, montoBox),
                new Separator(),
                new VBox(4, lNum, txtNum),
                new VBox(4, lNombre, txtNombre),
                fila1,
                new VBox(4, lTipo, selectorTarjeta),
                panelProgreso,
                lblMsg
        );
        body.setPadding(new Insets(20, 28, 10, 28));

        BorderPane root = new BorderPane();
        root.setTop(header);
        root.setCenter(body);
        root.setBottom(footer);
        root.setStyle("-fx-background-color: #f4f6f8;");

        dialog.setScene(new Scene(root, 440, 620));
        dialog.showAndWait();
    }

    private Label campoLabel(String texto) {
        Label l = new Label(texto);
        l.setFont(Font.font("System", FontWeight.BOLD, 12));
        l.setTextFill(Color.web("#555"));
        return l;
    }

    private void setError(Label lbl, String msg) {
        lbl.setTextFill(Color.web("#c62828"));
        lbl.setText(msg);
    }


    @FXML
    void onSeleccionarTicket(ActionEvent event) {
        String tipo = cmbTipoTicket.getValue();
        boolean familiar = "Familiar".equals(tipo);
        panelFamiliar.setVisible(familiar);
        panelFamiliar.setManaged(familiar);
        lblCostoFinal.setText("—");

        if (tipo == null) return;
        switch (tipo) {
            case "General"   -> lblPrecioBase.setText("$" + String.format("%,.0f", PRECIO_GENERAL)   + " / entrada");
            case "Familiar"  -> lblPrecioBase.setText("$" + String.format("%,.0f", PRECIO_FAMILIAR)  + " / integrante  (−15% desde 4 personas)");
            case "Fast-Pass" -> lblPrecioBase.setText("$" + String.format("%,.0f", PRECIO_FASTPASS)  + " (incluye +50% de recargo VIP)");
        }
    }

    @FXML
    void onComprarTicket(ActionEvent event) {
        if (visitante == null) {
            alerta("Sin perfil", "Primero registre su perfil en la pestaña 'Mi Perfil'.");
            return;
        }
        String tipo = cmbTipoTicket.getValue();
        if (tipo == null) {
            alerta("Datos incompletos", "Seleccione el tipo de ticket.");
            return;
        }

        try {
            String idTicket = "TKT-" + System.currentTimeMillis();
            Ticket ticket;

            switch (tipo) {
                case "Familiar" -> {
                    String numStr = txtNumIntegrantes.getText().trim();
                    int num = numStr.isEmpty() ? 1 : Integer.parseInt(numStr);
                    if (num < 1) { alerta("Integrantes inválido", "Ingrese al menos 1 integrante."); return; }
                    ticket = new TicketFamiliar(idTicket, PRECIO_FAMILIAR, num);
                }
                case "Fast-Pass" -> ticket = new TicketFastPass(idTicket, PRECIO_FASTPASS);
                default          -> ticket = new TicketGeneral(idTicket, PRECIO_GENERAL);
            }

            double costoFinal = ticket.calcularCostoFinal();
            lblCostoFinal.setText("$" + String.format("%,.0f", costoFinal));

            if (!visitante.descontarSaldo(costoFinal)) {
                alerta("Saldo insuficiente",
                        "Saldo actual: $" + String.format("%,.0f", visitante.getSaldoVirtual())
                                + "\nCosto requerido: $" + String.format("%,.0f", costoFinal));
                return;
            }
            visitante.comprarTicket(ticket);
            lblSaldo.setText(String.format("%.2f", visitante.getSaldoVirtual()));
            actualizarInfoTicket();
            lblResultadoTicket.setText("¡Ticket " + tipo + " adquirido correctamente!");
            info("Compra exitosa",
                    "Ticket adquirido.\nTipo: " + tipo
                            + "\nCosto final: $" + String.format("%,.0f", costoFinal));

        } catch (NumberFormatException ex) {
            alerta("Error de formato", "Ingrese un número válido de integrantes.");
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/techparkuq/vista/Login.fxml"));
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