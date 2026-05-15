package co.edu.uniquindio.techparkuq.modelo;

import co.edu.uniquindio.techparkuq.modelo.abstractas.Atraccion;
import co.edu.uniquindio.techparkuq.modelo.enums.AlertaClimatica;

public class AtraccionGeneral extends Atraccion {

    public AtraccionGeneral(String idUnico, String nombre, int capacidadMaximaCiclo, double alturaMinima, int edadMinima, double costoAdicional) {
        super(idUnico, nombre, capacidadMaximaCiclo, alturaMinima, edadMinima, costoAdicional);
    }

    @Override
    public void reaccionarAlClima(AlertaClimatica alerta) {
        if (alerta == AlertaClimatica.TORMENTA_ELECTRICA) {
            System.out.println("PRECAUCIÓN: " + getNombre() + " sigue operando, pero se recomienda precaución a los visitantes.");
        }
    }
}