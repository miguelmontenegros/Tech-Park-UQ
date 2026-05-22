package co.edu.uniquindio.techparkuq.modelo;

import co.edu.uniquindio.techparkuq.modelo.abstractas.Ticket;

public class TicketFamiliar extends Ticket {
    private static final int MINIMO_INTEGRANTES_DESCUENTO = 4;
    private static final double FACTOR_DESCUENTO = 0.85;

    private int numIntegrante;
    public TicketFamiliar(String idTicket, double precioBase, int numIntegrante){
        super(idTicket, precioBase);
        this.numIntegrante = numIntegrante;
    }

    @Override
    public double calcularCostoFinal() {
        double subtotal = getPrecioBase() * numIntegrante;
        if (numIntegrante >= MINIMO_INTEGRANTES_DESCUENTO) {
            return subtotal * FACTOR_DESCUENTO;
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
