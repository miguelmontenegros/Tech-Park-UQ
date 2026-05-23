package co.edu.uniquindio.techparkuq.modelo;

import co.edu.uniquindio.techparkuq.modelo.abstractas.Atraccion;
import co.edu.uniquindio.techparkuq.modelo.enums.AlertaClimatica;
import co.edu.uniquindio.techparkuq.modelo.enums.EstadoAtraccion;
import java.io.Serializable;

public class AtraccionMecanicaAltura extends Atraccion implements Serializable {

    private static final long serialVersionUID = 1L;
    private double alturaMaxima;

    public AtraccionMecanicaAltura(String idUnico, String nombre, int capacidadMaximaCiclo, double alturaMinima, int edadMinima, double costoAdicional, double alturaMaxima) {
        super(idUnico, nombre, capacidadMaximaCiclo, alturaMinima, edadMinima, costoAdicional);
        this.alturaMaxima = alturaMaxima;
    }

    @Override
    public void reaccionarAlClima(AlertaClimatica alerta) {
        if (alerta == AlertaClimatica.LLUVIA_FUERTE || alerta == AlertaClimatica.TORMENTA_ELECTRICA) {
            actualizarEstado(EstadoAtraccion.CERRADA, "Riesgo eléctrico/vientos por tormenta.");
            System.out.println("ALERTA CLIMA: La atracción mecánica " + getNombre() + " ha sido CERRADA.");
        } else if (alerta == AlertaClimatica.NINGUNA && getEstado() == EstadoAtraccion.CERRADA) {
            actualizarEstado(EstadoAtraccion.ACTIVA, "");
            System.out.println("INFO: " + getNombre() + " operativa nuevamente.");
        }
    }

    public double getAlturaMaxima() { return alturaMaxima; }
    public void setAlturaMaxima(double alturaMaxima) { this.alturaMaxima = alturaMaxima; }
}