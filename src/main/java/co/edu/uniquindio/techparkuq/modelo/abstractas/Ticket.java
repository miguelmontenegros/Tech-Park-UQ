package co.edu.uniquindio.techparkuq.modelo.abstractas;

import java.io.Serializable;

public abstract class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;
    private String idTicket;
    private double precioBase;
    private boolean activo;

    public Ticket(String idTicket, double precioBase) {
        this.idTicket = idTicket;
        this.precioBase = precioBase;
        this.activo = true;
    }

    public boolean isActivo() {
        return activo;
    }

    public abstract double calcularCostoFinal();

    public String getIdTicket() {
        return idTicket;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}