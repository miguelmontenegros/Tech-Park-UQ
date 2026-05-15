package co.edu.uniquindio.techparkuq.modelo;

import co.edu.uniquindio.techparkuq.modelo.abstractas.Atraccion;
import co.edu.uniquindio.techparkuq.modelo.enums.AlertaClimatica;
import co.edu.uniquindio.techparkuq.modelo.enums.EstadoAtraccion;

public class AtraccionAcuatica extends Atraccion {

    private boolean requiereTrajeAcuatico;

    public AtraccionAcuatica(String idUnico, String nombre, int capacidadMaximaCiclo, double alturaMinima, int edadMinima, double costoAdicional, boolean requiereTrajeAcuatico) {
        super(idUnico, nombre, capacidadMaximaCiclo, alturaMinima, edadMinima, costoAdicional);
        this.requiereTrajeAcuatico = requiereTrajeAcuatico;
    }

    @Override
    public void reaccionarAlClima(AlertaClimatica alerta) {

        if (alerta == AlertaClimatica.LLUVIA_FUERTE || alerta == AlertaClimatica.TORMENTA_ELECTRICA) {
            actualizarEstado(EstadoAtraccion.CERRADA, "Cierre preventivo: Clima no apto para atracciones acuáticas.");
            System.out.println("ALERTA CLIMA: " + getNombre() + " ha sido CERRADA por seguridad.");

        } else if (alerta == AlertaClimatica.NINGUNA && getEstado() == EstadoAtraccion.CERRADA) {
            actualizarEstado(EstadoAtraccion.ACTIVA, "");
            System.out.println("INFO: " + getNombre() + " reabierta. El clima ha mejorado.");
        }
    }

    public boolean isRequiereTrajeAcuatico() { return requiereTrajeAcuatico; }
    public void setRequiereTrajeAcuatico(boolean requiereTrajeAcuatico) { this.requiereTrajeAcuatico = requiereTrajeAcuatico; }
}