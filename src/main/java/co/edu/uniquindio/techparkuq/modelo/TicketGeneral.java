package co.edu.uniquindio.techparkuq.modelo;


import co.edu.uniquindio.techparkuq.modelo.abstractas.Ticket;

public class TicketGeneral extends Ticket {

    public TicketGeneral(String idTicket, double precioBase) {
        super(idTicket, precioBase);
    }
    @Override
    public double calcularCostoFinal() {
        return getPrecioBase();
    }

}

