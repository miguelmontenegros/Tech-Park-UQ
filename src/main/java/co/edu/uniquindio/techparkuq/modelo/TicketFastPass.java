package co.edu.uniquindio.techparkuq.modelo;

import co.edu.uniquindio.techparkuq.modelo.abstractas.Ticket;
import java.io.Serializable;

public class TicketFastPass extends Ticket implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final double PORCENTAJE_RECARGO = 1.5;

    public TicketFastPass(String idTicket, double precioBase) {
        super(idTicket, precioBase);
    }
    @Override
    public double calcularCostoFinal(){
        return getPrecioBase()*PORCENTAJE_RECARGO;
    }
    public int obtenerPrioridadCola(){
        return 1;
    }
}