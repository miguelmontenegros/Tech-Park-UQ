package co.edu.uniquindio.techparkuq.modelo;

import co.edu.uniquindio.techparkuq.modelo.abstractas.Ticket;

public class TicketFastPass extends Ticket {

    public TicketFastPass(String idTicket, double precioBase) {
        super(idTicket, precioBase);
    }
    @Override
    public double calcularCostoFinal(){
        return getPrecioBase()*1.5;
    }
    public int obtenerPrioridadCola(){
        return 1;
    }
}

