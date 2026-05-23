package co.edu.uniquindio.techparkuq.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;

public class RegistroVisita implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nombreAtraccion;
    private LocalDateTime fecha;

    public RegistroVisita(String nombreAtraccion) {
        this.nombreAtraccion = nombreAtraccion;
        this.fecha = LocalDateTime.now();
    }

    public String getNombreAtraccion() { return nombreAtraccion; }
    public LocalDateTime getFecha() { return fecha; }
}