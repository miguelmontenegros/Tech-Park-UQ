module co.edu.uniquindio.techparkuq {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens co.edu.uniquindio.techparkuq to javafx.fxml;
    exports co.edu.uniquindio.techparkuq;
}