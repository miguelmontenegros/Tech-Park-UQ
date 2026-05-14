package co.edu.uniquindio.techparkuq.modelo.abstractas;

public abstract class Ticket {
    private String idTicket;
    private double precioBase;
    private boolean activo; // Control de validez del ingreso

    public Ticket(String idTicket, double precioBase) {
        this.idTicket = idTicket;
        this.precioBase = precioBase;
        this.activo = true; // Inicia activo por defecto
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

