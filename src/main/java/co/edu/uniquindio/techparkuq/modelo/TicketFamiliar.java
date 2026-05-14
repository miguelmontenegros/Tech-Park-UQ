package co.edu.uniquindio.techparkuq.modelo;

import co.edu.uniquindio.techparkuq.modelo.abstractas.Ticket;
public class TicketFamiliar extends Ticket {
private int numIntegrante;
    public TicketFamiliar(String idTicket, double precioBase, int numIntegrante){
        super(idTicket, precioBase);
        this.numIntegrante = numIntegrante;
    }

    @Override
    public double calcularCostoFinal() {
        double subtotal = getPrecioBase() * numIntegrante;

        if (numIntegrante >= 4) {
            return subtotal * 0.85;
        }

        return subtotal;
    }

    public int getNumIntegrante() {
        return numIntegrante;
    }

    public void setNumIntegrante(int numIntegrante) {
        this.numIntegrante = numIntegrante;
    }
}
