package co.edu.uniquindio.techparkuq.modelo;

import java.time.LocalDate;
import java.io.Serializable;

public class RevisionTecnica implements Serializable {

    private static final long serialVersionUID = 1L;
    private String idRevision;
    private LocalDate fecha;
    private String observaciones;
    private boolean exitosa;

    public RevisionTecnica(String idRevision, LocalDate fecha, String observaciones, boolean exitosa) {
        this.idRevision = idRevision;
        this.fecha = fecha;
        this.observaciones = observaciones;
        this.exitosa = exitosa;
    }

    public String getIdRevision() {
        return idRevision;
    }

    public void setIdRevision(String idRevision) {
        this.idRevision = idRevision;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public boolean isExitosa() {
        return exitosa;
    }

    public void setExitosa(boolean exitosa) {
        this.exitosa = exitosa;
    }

    @Override
    public String toString() {
        return "RevisionTecnica {" +
                "ID: '" + idRevision + '\'' +
                ", Fecha: " + fecha +
                ", Estado: " + (exitosa ? "EXITOSA" : "FALLIDA") +
                ", Notas: '" + observaciones + '\'' +
                '}';
    }
}