package co.edu.uniquindio.techparkuq.modelo;

import co.edu.uniquindio.techparkuq.modelo.abstractas.Atraccion;
import co.edu.uniquindio.techparkuq.modelo.enums.AlertaClimatica;

public class AtraccionShow extends Atraccion {

    private String horarioFuncion;

    public AtraccionShow(String idUnico, String nombre, int capacidadMaximaCiclo, double alturaMinima, int edadMinima, double costoAdicional, String horarioFuncion) {
        super(idUnico, nombre, capacidadMaximaCiclo, alturaMinima, edadMinima, costoAdicional);
        this.horarioFuncion = horarioFuncion;
    }

    @Override
    public void reaccionarAlClima(AlertaClimatica alerta) {
        if (alerta == AlertaClimatica.TORMENTA_ELECTRICA || alerta == AlertaClimatica.LLUVIA_FUERTE) {
            System.out.println("INFO SHOW: El show '" + getNombre() + "' continúa con normalidad en su teatro techado.");
        }
    }

    public String getHorarioFuncion() { return horarioFuncion; }
    public void setHorarioFuncion(String horarioFuncion) { this.horarioFuncion = horarioFuncion; }
}