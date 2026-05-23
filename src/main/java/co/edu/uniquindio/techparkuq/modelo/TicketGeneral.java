package co.edu.uniquindio.techparkuq.modelo;

import co.edu.uniquindio.techparkuq.modelo.abstractas.Ticket;
import java.io.Serializable;

public class TicketGeneral extends Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    public TicketGeneral(String idTicket, double precioBase) {
        super(idTicket, precioBase);
    }
    @Override
    public double calcularCostoFinal() {
        return getPrecioBase();
    }
}
